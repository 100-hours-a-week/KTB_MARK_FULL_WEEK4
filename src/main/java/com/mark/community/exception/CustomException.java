package com.mark.community.exception;

import com.mark.community.messages.ApiResponseErrorMessage;

public class CustomException extends RuntimeException{

    private final ApiResponseErrorMessage errorCode;

    public CustomException(ApiResponseErrorMessage errorCode){
        this.errorCode = errorCode;
    }

    public ApiResponseErrorMessage getErrorCode(){
        return errorCode;
    }
}
