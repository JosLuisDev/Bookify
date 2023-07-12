package com.accenture.demobookify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice //Permite que este controlador sea un manejardor de excepciones
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex){
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("The max size to upload an image was exceeded. Max size = 500 KB");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleImageFormatNotSupported(NullPointerException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The image format that is supported is PNG.");
    }

}