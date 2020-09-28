package com.ppd.mubler.controllers.webservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ppd.mubler.model.entity.log4j.MublerLogger;
import com.ppd.mubler.model.entity.SecurityToken;
import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.service.TokenService;
import com.ppd.mubler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    private MublerLogger mublerLogger = MublerLogger.getInstance();

    @Autowired
    Environment env;

    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity connect(@RequestBody User infos) {
        mublerLogger.logInfo("URL = /login\nMethod = POST\n RequestBody = " + infos + "\n");
        User user = userService.getUserByEmailAndPassword(infos.getEmail(), infos.getPassword());
        if (user != null) {
            SecurityToken securityToken = tokenService.generateToken(user);
            mublerLogger.logInfo("URL = /login\nMethod = POST\n Return Body = " + securityToken + "\nReturn code = 200");
            return new ResponseEntity<>(securityToken, HttpStatus.OK);
        } else {
            mublerLogger.logInfo("URL = /login\nMethod = POST\nReturn code = 403");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseEntity verify(@RequestBody String token) {

        Algorithm algorithm = Algorithm.HMAC256(env.getProperty("jwt.secret"));

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("mubler")
                    .build();

            DecodedJWT jwt = verifier.verify(token);

        } catch (JWTVerificationException exception) {
            System.err.println("Erreur token");
            return new ResponseEntity<>(new SecurityToken(token), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(new SecurityToken(token), HttpStatus.OK);
    }

}
