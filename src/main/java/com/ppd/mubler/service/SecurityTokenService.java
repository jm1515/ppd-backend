package com.ppd.mubler.service;

import com.ppd.mubler.model.entity.SecurityToken;
import com.ppd.mubler.model.entity.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityTokenService {

    private static Map<SecurityToken, User> validTokenList = new HashMap<>();

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String generateToken(){
        return "Mubler"+ RandomStringUtils.random(512, CHARACTERS);
    }

    public static void addValidToken(SecurityToken securityToken, User user){
        validTokenList.put(securityToken, user);
    }

    public static void removeValidToken(SecurityToken securityToken){
        validTokenList.remove(securityToken);
    }

    public static boolean verifyToken(SecurityToken securityToken){
        for (SecurityToken validToken: validTokenList.keySet()) {
            if (validToken.getToken().equals(securityToken.getToken())){
                return true;
            }
        }
        return false;
    }

    public static User getUserFromToken(String token){
        for (SecurityToken securityToken: validTokenList.keySet()) {
            if (securityToken.getToken().equals(token)){
                return validTokenList.get(securityToken);
            }
        }
        return null;
    }
}
