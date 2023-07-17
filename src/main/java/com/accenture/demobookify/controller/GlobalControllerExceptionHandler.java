package com.accenture.demobookify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //Permite que este controlador sea un manejardor de excepciones
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException(){
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("The max size to upload an image was exceeded. Max size = 500 KB");
    }

    @ExceptionHandler(NullPointerException.class)//El unico metodo que arroja este error ha sido el de subir imagen porque solo soporta png
    public ResponseEntity<String> handleImageFormatNotSupported(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The image format that is supported is PNG.");
    }

    //Manejar el error de fecha invalida en dateOfBirth de Author
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleNotValidDateException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Property dateOfBirth invalid. You need to use this format yyyy-MM-dd and use a valid Date.");
    }

    //Mejor manera de manejar las excepciones para que salgan todos los errores en la peticion, igualmente puede no regresar
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgumentException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    //Validacion del ENUM de order
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> hanldeInvalidValueForOrderStatus(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}