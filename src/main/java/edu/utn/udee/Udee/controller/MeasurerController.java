package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Measurer;
import edu.utn.udee.Udee.dto.MeasurerDto;
import edu.utn.udee.Udee.service.MeasurerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurer")
public class MeasurerController {

    private final MeasurerService measurerService;

    @Autowired
    public MeasurerController(MeasurerService measurerService) {
        this.measurerService = measurerService;
    }



    @PostMapping
    public ResponseEntity addMeasurer (@RequestBody MeasurerDto measurerDto){
        measurerService.addMeasurer(Measurer.builder().
                        serialNumber(measurerDto.getSerialNumber()).
                        brand(measurerDto.getBrand()).
                        model(measurerDto.getModel()).
                        measurement(measurerDto.getMeasurement()).
                        build()
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}
