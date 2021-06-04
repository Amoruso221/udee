package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Measurer;
import edu.utn.udee.Udee.dto.MeasurerDto;
import edu.utn.udee.Udee.service.MeasurerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static edu.utn.udee.Udee.TestUtils.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MeasurerControllerTest {

    MeasurerController measurerController;

    ModelMapper modelMapper;

    @Mock
    MeasurerService measurerService;

    @Before
    public void setUp(){
        initMocks(this);
        measurerController = new MeasurerController(measurerService, modelMapper);
    }

    @Test
    public void addMeasurerIsOk(){
        //Given
        when(measurerService.addMeasurer(createMeasurerWithBrandAndModel())).
                thenReturn(createNewMeasurer());
        //Then
        ResponseEntity response = measurerController.addMeasurer(modelMapper.map(createMeasurerWithBrandAndModel(), MeasurerDto.class));
        //Asserts
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createNewMeasurer(), response.getBody());
    }


}