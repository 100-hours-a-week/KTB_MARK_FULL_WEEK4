package com.mark.community.messages;

public enum ErrorMessage {
    FAIL_UPLOAD("파일 업로드에 실패했습니다.");

    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
