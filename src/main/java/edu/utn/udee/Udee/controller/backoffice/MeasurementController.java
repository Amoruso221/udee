package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.dto.MeasurementDto;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.service.MeasurementService;
import edu.utn.udee.Udee.service.MeterService;
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
@RequestMapping("/api/backoffice/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeterService meterService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeterService meterService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.meterService = meterService;
        this.modelMapper = modelMapper;
    }

    private URI getLocation (Measurement measurement) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(measurement.getIdMeasurement())
                .toUri();
    }



    //***ADD NEW***//
    @PostMapping(consumes = "application/json")
    public ResponseEntity addMeasurement (@RequestBody MeasurementDto measurementDto)
            throws MeterNotExistsException {
        Measurement newMeasurement = measurementService.addMeasurement(Measurement.builder().
                    idMeasurement(measurementDto.getIdMeasurement()).
                    kwh(measurementDto.getKwh()).
                    dateTime(measurementDto.getDateTime()).
                meter(meterService.getBySerialNumber(measurementDto.getMeter().getSerialNumber())).
                    build());
//        Measurement measurement = Measurement.builder().
//                idMeasurement(measurementDto.getIdMeasurement()).
//                kwh(measurementDto.getKwh()).
//                dateTime(measurementDto.getDateTime()).
//                build();
//        if (measurementDto.getMeter().getSerialNumber() != null)
//            measurement.setMeter(meterService.getBySerialNumber(measurementDto.getMeter().getSerialNumber()));
//        measurementService.addMeasurement(measurement);
//        **********
//        Meter meter = modelMapper.map(measurementDto.getMeter(), Meter.class);
//        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);
//        measurement.setMeter(meter);
//        measurementService.addMeasurement(measurement);
//        **********
//        MeterDto meterDto = measurementDto.getMeter();
//        if (measurementDto.getMeter() != null) {
//            measurementService.addMeasurement(Measurement.builder().
//                    idMeasurement(measurementDto.getIdMeasurement()).
//                    kwh(measurementDto.getKwh()).
//                    dateTime(measurementDto.getDateTime()).
//                    meter(meterService.getBySerialNumber(measurementDto.getMeter().getSerialNumber())).
////                    meter(Meter.builder().
////                            serialNumber(meterDto.getSerialNumber()).
////                            brand(meterDto.getBrand()).
////                            model(meterDto.getModel()).
////                            measurement(meterDto.getMeasurement()).
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
        return ResponseEntity.created(getLocation(newMeasurement)).build();
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
            throws MeterNotExistsException {
        measurementService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
