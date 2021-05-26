package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AddressControllerAdvice {

    @ExceptionHandler(value = {AddressExistsException.class})
    public ResponseEntity<ErrorMessage> addressExists(){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessage.builder().code("AAE").message("Address already exist!").build());
    }

    @ExceptionHandler(value = {AddressNotExistsException.class})
    public ResponseEntity<ErrorMessage> addressNotExists(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
