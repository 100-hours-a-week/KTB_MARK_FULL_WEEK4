package com.mark.community.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mark.community.messages.ApiResponseMessage;


@JsonPropertyOrder({"message", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final String message;
    private final T data;

    public ApiResponse(ApiResponseMessage message){
        this.message = message.getMessage();
        this.data = null;
    }

    public ApiResponse(ApiResponseMessage message, T data){
        this.message = message.getMessage();
        this.data = data;
    }


    public String getMessage(){
        return message;
    }

    public T getData(){
        return data;
    }




}
