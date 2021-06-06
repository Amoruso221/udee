package edu.utn.udee.Udee.controller.advice;

import edu.utn.udee.Udee.exceptions.ErrorMessage;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MeasurerControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {MeterNotExistsException.class})
    public ResponseEntity<ErrorMessage> measurerNotExists(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(ErrorMessage.builder().code("MRNE").message("THE MEASURER DOES NO EXISTS.").
                        build());
    }

}
