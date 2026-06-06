package com.mark.community.response;

import com.mark.community.messages.ApiResponseErrorMessage;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message){
        this.message = message;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ApiResponseErrorMessage errorCode){
        return ResponseEntity
                .status(errorCode.getStatusCode().value())
                .body(new ErrorResponse(errorCode.getMessage()));
    }

    public String getMessage(){
        return message;
    }
}
