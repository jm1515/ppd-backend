package com.ppd.mubler.controllers.webservice;

import com.ppd.mubler.model.entity.*;
import com.ppd.mubler.model.entity.log4j.MublerLogger;
import com.ppd.mubler.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MublerService mublerService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MublerRequestService mublerRequestService;

    @Autowired
    TokenService tokenService;

    private MublerLogger mublerLogger = MublerLogger.getInstance();

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        mublerLogger.logInfo("URL = /users Method = POST RequestBody = " + user);
        try {
            userService.save(user);
        } catch (Exception e) {
            mublerLogger.logInfo("URL = /users Method = POST RequestBody = " + user + " Error = " + e + " Return code = 400");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        mublerLogger.logInfo("URL = /users Method = POST ReturnBody = " + user + " Return code = 201");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updatePassword(@RequestBody User user, @PathVariable long id) {
        mublerLogger.logInfo("URL = /users/" + id + "Method = PUT RequestBody = " + user);
        try {
            if (userService.getUserById(id) != null) {
                user.setId(id);
                userService.save(user);
            } else {
                mublerLogger.logInfo("URL = /users Method = PUT RequestBody = " + user +"Return code = 404");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            mublerLogger.logInfo("URL = /users Method = PUT RequestBody = " + user + " Error = " + e + " Return code = 400");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        mublerLogger.logInfo("URL = /users Method = POST ReturnBody = " + user + " Return code = 201");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestHeader(value="Authorization", required=false, defaultValue = "") String token) {
        mublerLogger.logInfo("URL = /users Method = GET");
        if (tokenService.verify(new SecurityToken(token))){
            mublerLogger.logInfo("URL = /users Method = GET Return code = 200");
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } else {
            mublerLogger.logInfo("URL = /users Method = GET Return code = 403");
            return new ResponseEntity<>(new HttpResponseMessage(403, "Unauthorized"),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserDetails(@RequestHeader(value="Authorization", required=false, defaultValue = "") String token, @PathVariable long id) {
        mublerLogger.logInfo("URL = /user/" + id + " Method = GET");
        if (tokenService.verify(new SecurityToken(token))){
            mublerLogger.logInfo("URL = /user/" + id + " Method = GET Return code = 200");
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } else {
            mublerLogger.logInfo("URL = /user/" + id + " Method = GET Return code = 403");
            return new ResponseEntity<>(new HttpResponseMessage(403, "Unauthorized"),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity getUser(@RequestHeader(value="Authorization", required=false, defaultValue = "") String token) {
        mublerLogger.logInfo("URL = /user Method = GET");
        mublerLogger.logInfo("URL = /user Method = GET token : " + token );

        if (tokenService.verify(new SecurityToken(token))){
            mublerLogger.logInfo("URL = /user Method = GET Return code = 200");
            return new ResponseEntity<>(userService.getUserById(tokenService.getIdByToken(new SecurityToken(token))), HttpStatus.OK);
        } else {
            mublerLogger.logInfo("URL = /user Method = GET Return code = 403");
            return new ResponseEntity<>(new HttpResponseMessage(403, "Unauthorized"),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/mublers", method = RequestMethod.POST)
    public ResponseEntity<Mubler> saveMubler(@RequestBody Mubler mubler) {
        mublerLogger.logInfo("URL = /mublers Method = POST RequestBody = " + mubler);
        try {
            mublerService.save(mubler);
        }catch (Exception e) {
            mublerLogger.logInfo("URL = /mublers Method = POST RequestBody = " + mubler + " Error = " + e + " Return code = 400");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        mublerLogger.logInfo("URL = /mublers Method = POST RetruntBody = " + mubler + " Return code = 201");
        return new ResponseEntity<>(mubler, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{idUser}/reviewsof", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> getReviewsByIdUser(@PathVariable long idUser){
        mublerLogger.logInfo("URL = /users/"+ idUser +"/reviews, Method = GET");
        User user = this.userService.getUserById(idUser);
        if (user == null){
            mublerLogger.logInfo("URL = /users/"+ idUser +"/reviews, Method = GET, Return code = 404");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Review> reviews = this.reviewService.getReviewsByIdWriter(user.getId());
        mublerLogger.logInfo("URL = /users/"+ idUser +"/reviews, Method = GET, ReturnBody = " + reviews + ",Return code = 200");
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{idUser}/reviewsconcerned", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> getReviewsByIdMubler(@PathVariable long idUser){
        mublerLogger.logInfo("URL = /mublers/"+ idUser +"/reviews, Method = GET");
        User user = this.userService.getUserById(idUser);
        if (user == null){
            mublerLogger.logInfo("URL = /mublers/"+ idUser +"/reviews, Method = GET, Return code = 404");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Review> reviews = this.reviewService.getReviewsByIdConcerned(user.getId());
        mublerLogger.logInfo("URL = /mublers/"+ idUser +"/reviews, Method = GET, ReturnBody = " + reviews + ",Return code = 200");
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{idUser}/history", method = RequestMethod.GET)
    public ResponseEntity getRequestHistory(@PathVariable long idUser, @RequestHeader(value="Authorization", required=false, defaultValue = "") String token){
        if (tokenService.verify(new SecurityToken(token))){
            mublerLogger.logInfo("URL = /user/"+idUser+"/history Method = GET Return code = 200");
            return new ResponseEntity<>(mublerRequestService.getMublerRequestsByIdRequester(idUser) ,HttpStatus.OK);
        } else {
            mublerLogger.logInfo("URL = /user/"+idUser+"/history Method = GET Return code = 403");
            return new ResponseEntity<>(new HttpResponseMessage(403, "Unauthorized"),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/users/{idUser}/historydone", method = RequestMethod.GET)
    public ResponseEntity getRequestHistoryDone(@PathVariable long idUser, @RequestHeader(value="Authorization", required=false, defaultValue = "") String token){
        if (tokenService.verify(new SecurityToken(token))){
            mublerLogger.logInfo("URL = /user/"+idUser+"/history Method = GET Return code = 200");
            return new ResponseEntity<>(mublerRequestService.getMublerRequestsByIdAccepter(idUser) ,HttpStatus.OK);
        } else {
            mublerLogger.logInfo("URL = /user/"+idUser+"/history Method = GET Return code = 403");
            return new ResponseEntity<>(new HttpResponseMessage(403, "Unauthorized"),HttpStatus.FORBIDDEN);
        }
    }
}
