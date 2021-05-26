package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.exceptions.ErrorMessage;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MeasurementControllerAdvice {

    @ExceptionHandler(value = {MeasurementNotExistsException.class})
    public ResponseEntity<ErrorMessage> MeasurementNotExists(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(ErrorMessage.builder().code("MTNE").message("MEASUREMENT NO EXIST.").
                        build());
    }

}
