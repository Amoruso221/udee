package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.exceptions.ClientExistsException;
import edu.utn.udee.Udee.exceptions.ClientNotExistsException;
import edu.utn.udee.Udee.exceptions.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientControllerAdvice {

    @ExceptionHandler(value = {ClientExistsException.class})
    public ResponseEntity<ErrorMessage> clientExists(){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessage.builder().code("CAE").message("Client already exist!").build());
    }

    @ExceptionHandler(value = {ClientNotExistsException.class})
    public ResponseEntity<ErrorMessage> clientNotExists(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
