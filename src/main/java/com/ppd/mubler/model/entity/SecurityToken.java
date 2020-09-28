package com.ppd.mubler.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.util.codec.binary.Base64;

public class SecurityToken {
    private String token;

    Base64 base64Url = new Base64(true);

    public SecurityToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    public String getHeader() {
        return new String(base64Url.decode(token.split("\\.")[0]));
    }

    @JsonIgnore
    public String getBody() {
        return new String(base64Url.decode(token.split("\\.")[1]));
    }

    @JsonIgnore
    public String getSignature() {
        return new String(base64Url.decode(token.split("\\.")[2]));
    }

    @Override
    public String toString() {
        return "SecurityToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
