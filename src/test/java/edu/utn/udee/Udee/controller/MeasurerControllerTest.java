package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.dto.MeterDto;
import edu.utn.udee.Udee.service.MeterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static edu.utn.udee.Udee.TestUtils.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MeasurerControllerTest {

    MeterController measurerController;

    ModelMapper modelMapper;

    @Mock
    MeterService meterService;

    @Before
    public void setUp(){
        initMocks(this);
        measurerController = new MeterController(meterService, modelMapper);
    }

    @Test
    public void addMeasurerIsOk(){
        //Given
        when(meterService.addMeter(createMeasurerWithBrandAndModel())).
                thenReturn(createNewMeasurer());
        //Then
        ResponseEntity response = measurerController.addMeter(modelMapper.map(createMeasurerWithBrandAndModel(), MeterDto.class));
        //Asserts
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createNewMeasurer(), response.getBody());
    }


}