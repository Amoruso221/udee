package edu.utn.udee.Udee.controller.advice;

import edu.utn.udee.Udee.exceptions.ErrorMessage;
import edu.utn.udee.Udee.exceptions.MeasurerNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MeasurerControllerAdvice {

    @ExceptionHandler(value = {MeasurerNotExistsException.class})
    public ResponseEntity<ErrorMessage> MeasurerNotExists(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(ErrorMessage.builder().code("MRNE").message("MEASURER NO EXISTS.").
                        build());
    }

}
