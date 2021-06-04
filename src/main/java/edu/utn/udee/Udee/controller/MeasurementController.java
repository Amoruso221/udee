package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Measurer;
import edu.utn.udee.Udee.dto.MeasurementDto;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurerNotExistsException;
import edu.utn.udee.Udee.service.MeasurementService;
import edu.utn.udee.Udee.service.MeasurerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurerService measurerService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurerService measurerService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurerService = measurerService;
        this.modelMapper = modelMapper;
    }



    //***ADD NEW***//
    @PostMapping(consumes = "application/json")
    public ResponseEntity addMeasurement (@RequestBody MeasurementDto measurementDto)
            throws MeasurerNotExistsException {
        Measurement newMeasurement = measurementService.addMeasurement(Measurement.builder().
                    idMeasurement(measurementDto.getIdMeasurement()).
                    kwh(measurementDto.getKwh()).
                    dateTime(measurementDto.getDateTime()).
                    measurer(measurerService.getBySerialNumber(measurementDto.getMeasurer().getSerialNumber())).
                    build());
//        Measurement measurement = Measurement.builder().
//                idMeasurement(measurementDto.getIdMeasurement()).
//                kwh(measurementDto.getKwh()).
//                dateTime(measurementDto.getDateTime()).
//                build();
//        if (measurementDto.getMeasurer().getSerialNumber() != null)
//            measurement.setMeasurer(measurerService.getBySerialNumber(measurementDto.getMeasurer().getSerialNumber()));
//        measurementService.addMeasurement(measurement);
//        **********
//        Measurer measurer = modelMapper.map(measurementDto.getMeasurer(), Measurer.class);
//        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);
//        measurement.setMeasurer(measurer);
//        measurementService.addMeasurement(measurement);
//        **********
//        MeasurerDto measurerDto = measurementDto.getMeasurer();
//        if (measurementDto.getMeasurer() != null) {
//            measurementService.addMeasurement(Measurement.builder().
//                    idMeasurement(measurementDto.getIdMeasurement()).
//                    kwh(measurementDto.getKwh()).
//                    dateTime(measurementDto.getDateTime()).
//                    measurer(measurerService.getBySerialNumber(measurementDto.getMeasurer().getSerialNumber())).
////                    measurer(Measurer.builder().
////                            serialNumber(measurerDto.getSerialNumber()).
////                            brand(measurerDto.getBrand()).
////                            model(measurerDto.getModel()).
////                            measurement(measurerDto.getMeasurement()).
////                            build()).
//                    build());
//        }
//        else{
//            measurementService.addMeasurement(Measurement.builder().
//                    idMeasurement(measurementDto.getIdMeasurement()).
//                    kwh(measurementDto.getKwh()).
//                    dateTime(measurementDto.getDateTime()).
//                    build());
//        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMeasurement.getIdMeasurement())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //***GET ALL***//
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MeasurementDto>> getAll(Pageable pageable){
        Page page = measurementService.getAll(pageable);
        return ResponseEntity.
                status(HttpStatus.OK).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    //***GET BY ID***//
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<MeasurementDto> getById (@PathVariable Integer id)
            throws MeasurementNotExistsException {
        Measurement measurement = measurementService.getById(id);
        return ResponseEntity.ok(MeasurementDto.from(measurement));
    }

    //***DELETE BY ID***//
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteMeasurement(@PathVariable Integer id)
            throws MeasurerNotExistsException{
        measurementService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
