package com.event_management.management.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHelper {

    public static ResponseEntity<Object> createResponse(HttpStatus status, String message, Object payload, Map<String,Object> additionalFields){
        Map<String,Object> response = new HashMap<>();
        response.put("Status",status.value());
        response.put("message",message);
        response.put("data",payload);

        if(additionalFields != null){
            response.putAll(additionalFields);
        }

        return new ResponseEntity<>(response,status);
    }

    public static ResponseEntity<Object> createErrorResponse(HttpStatus status,String error,boolean returnStackTrace,Throwable throwable){
        Map<String,Object> response = new HashMap<>();
        response.put("status",status.value());
        response.put("message",error);

        if(returnStackTrace && throwable != null){
            response.put("stackTrace",throwable.getMessage());
        }

        System.out.println("Error: "+error + (throwable != null ? ", " + throwable.getMessage() :""));

        return new ResponseEntity<>(response,status);
    }

}

