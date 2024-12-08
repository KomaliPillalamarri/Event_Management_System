package com.event_management.management.helpers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorHelper {

    public static Map<String,String> handleValidationErrors(BindingResult bindingResult){
        Map<String,String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()){
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return errors;
    }

    public static String handleControllerError(Exception error) {
        return error.getMessage() != null ? error.getMessage() : "An error occurred.";
    }
}
