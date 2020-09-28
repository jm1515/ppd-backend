package com.ppd.mubler.controllers.webservice;

import com.ppd.mubler.model.entity.HttpResponseMessage;
import com.ppd.mubler.model.entity.SecurityToken;
import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.model.entity.log4j.MublerLogger;
import com.ppd.mubler.model.entity.MublerRequest;
import com.ppd.mubler.model.entity.exceptions.InvalidMublerRequestException;
import com.ppd.mubler.service.MublerRequestService;
import com.ppd.mubler.service.RequestBroadcastHandler;
import com.ppd.mubler.service.TokenService;
import com.ppd.mubler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MublerRequestController {

    @Autowired
    MublerRequestService mublerRequestService;
    @Autowired
    RequestBroadcastHandler requestBroadcastHandler;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;

    private MublerLogger mublerLogger = MublerLogger.getInstance();

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResponseEntity saveMublerRequest(@RequestBody MublerRequest mublerRequest, @RequestHeader(value="Authorization", required=false, defaultValue = "") String token) {
        mublerLogger.logInfo("URL = /request Method = POST RequestBody = " + mublerRequest);
        if (tokenService.verify(new SecurityToken(token))){
            User user = userService.getUserById(tokenService.getIdByToken(new SecurityToken(token)));
            mublerRequest.setIdRequester(user.getId());
            /*try {
                mublerRequestService.generateRequestPrice(mublerRequest);
            } catch (InvalidMublerRequestException e){
                mublerLogger.logInfo("URL = /request Method = POST RequestBody = " + mublerRequest + " Error = " + e
                        + " Return code = 400");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }*/
            mublerRequest.setPrice(30);
            mublerRequestService.save(mublerRequest);
            Thread broadcastThread = new Thread(() -> requestBroadcastHandler.broadcastRequest(mublerRequest));
            broadcastThread.start();
            mublerLogger.logInfo("URL = /request Method = POST RequestBody = " + mublerRequest
                    + " Return code = 201");
            return new ResponseEntity<>(mublerRequest, HttpStatus.CREATED);
        } else {
            mublerLogger.logInfo("URL = /request Method = GET Return code = 403");
            return new ResponseEntity<>(new HttpResponseMessage(403, "Unauthorized"),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/request/{id}/close", method = RequestMethod.POST)
    public ResponseEntity closeMublerRequest(@PathVariable long id, @RequestHeader(value="Authorization", required=false, defaultValue = "") String token){
        if (tokenService.verify(new SecurityToken(token))){
            MublerRequest mublerRequest = mublerRequestService.getMublerRequestById(id);
            mublerRequest.setOver(true);
            mublerRequestService.save(mublerRequest);
            mublerLogger.logInfo("URL = /request/"+id+"/close Method = GET Return code = 200");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            mublerLogger.logInfo("URL = /request/"+id+"/close Method = GET Return code = 403");
            return new ResponseEntity<>(new HttpResponseMessage(403, "Unauthorized"),HttpStatus.FORBIDDEN);
        }
    }
}
