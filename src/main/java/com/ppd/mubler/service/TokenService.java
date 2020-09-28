package com.ppd.mubler.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppd.mubler.model.entity.SecurityToken;
import com.ppd.mubler.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


@Component
public class TokenService {

    @Autowired
    Environment env;

    public boolean verify(SecurityToken securityToken){
        Algorithm algorithm = Algorithm.HMAC256(env.getProperty("jwt.secret"));

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(env.getProperty("jwt.issuer"))
                .build();
        try {
            verifier.verify(securityToken.getToken());

        } catch (JWTVerificationException exception) {
            return false;
        }
        return true;
    }

    public int getIdByToken(SecurityToken securityToken) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(securityToken.getBody());

            return Integer.parseInt(actualObj.get("id").toString());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public SecurityToken generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(env.getProperty("jwt.secret"));
        String token = JWT.create()
                .withIssuer(env.getProperty("jwt.issuer"))
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withExpiresAt(new Date(new java.util.Date().toInstant().plusSeconds(Integer.parseInt(env.getProperty("jwt.expiration"))).toEpochMilli()))
                .sign(algorithm);
        return new SecurityToken(token);
    }
}
