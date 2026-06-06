package com.mark.community.dto;

public class RegisterResponse {
    private String userId;

    public RegisterResponse(String userId) {
        this.userId = userId;
    }
    public RegisterResponse(){

    }

    public String getUserId(){
        return userId;
    }
}
