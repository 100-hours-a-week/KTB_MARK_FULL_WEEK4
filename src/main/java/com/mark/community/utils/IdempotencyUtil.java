package com.mark.community.utils;

import com.mark.community.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class IdempotencyUtil {
    private static Map<String, ResponseEntity<?>> idempotencyKeys = new HashMap<>();

    public static void setResponse(String idempotencyKey , ResponseEntity<?> responseEntity){
        idempotencyKeys.put(idempotencyKey, responseEntity);
    }

    public static ResponseEntity<?> getResponse(String idempotencyKey){
        return idempotencyKeys.get(idempotencyKey);
    }
}
