package com.example.productservice_proxy.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvices {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e){
        return new ResponseEntity<>("Internal Server Error Occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
