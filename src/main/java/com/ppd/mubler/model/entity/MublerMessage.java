package com.ppd.mubler.model.entity;

public class MublerMessage {

    private String name;

    public MublerMessage(){}

    public MublerMessage(String content){
        this.name = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
