package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Measurer;
import edu.utn.udee.Udee.dto.MeasurerDto;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurerNotExistsException;
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
@RequestMapping("/api/measurer")
public class MeasurerController {

    private final MeasurerService measurerService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurerController(MeasurerService measurerService, ModelMapper modelMapper) {
        this.measurerService = measurerService;
        this.modelMapper = modelMapper;
    }



    //***ADD NEW***//
    @PostMapping(consumes = "application/json")
    public ResponseEntity addMeasurer (@RequestBody MeasurerDto measurerDto){
//        measurerService.addMeasurer(Measurer.builder().
//                        serialNumber(measurerDto.getSerialNumber()).
//                        brand(measurerDto.getBrand()).
//                        model(measurerDto.getModel()).
//                        measurement(measurerDto.getMeasurement()).
//                        build());
        Measurer newMeasurer = measurerService.addMeasurer(modelMapper.map(measurerDto, Measurer.class));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMeasurer.getSerialNumber())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //***GET ALL***//
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MeasurerDto>> getAll(Pageable pageable){
            Page page = measurerService.getAll(pageable);
            return ResponseEntity.
                    status(HttpStatus.OK).
                    header("X-Total-Count", Long.toString(page.getTotalElements())).
                    header("X-Total-Pages", Long.toString(page.getTotalPages())).
                    body(page.getContent());
    }

    //***GET BY ID***//
    @GetMapping(path = "/{serialNumber}", produces = "application/json")
    public ResponseEntity<MeasurerDto> getBySerialNumber (@PathVariable Integer serialNumber)
            throws MeasurerNotExistsException {
        Measurer measurer = measurerService.getBySerialNumber(serialNumber);
        return ResponseEntity.ok(MeasurerDto.from(measurer));
    }

    //***DELETE BY ID***//
    @DeleteMapping(path = "/{serialNumber}", produces = "application/json")
    public ResponseEntity deleteMeasurer(@PathVariable Integer serialNumber)
            throws MeasurerNotExistsException{
        measurerService.deleteBySerialNumber(serialNumber);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //***ADD MEASUREMENT***//
    @PutMapping(path = "/{serialNumber}/measurement/{idMeasurement}", produces = "application/json")
    public ResponseEntity addMeasurementToMeasurer (@PathVariable Integer serialNumber, @PathVariable Integer idMeasurement)
            throws MeasurerNotExistsException, MeasurementNotExistsException {
        measurerService.addMeasurementToMeasurer(serialNumber, idMeasurement);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //***ADD ADDRESS***//
    @PutMapping(path = "/{serialNumber}/address/{id}", produces = "application/json")
    public ResponseEntity addAddressToMeasurer (@PathVariable Integer serialNumber, @PathVariable Integer id)
            throws MeasurerNotExistsException, AddressNotExistsException {
        measurerService.addAddressToMeasurer(serialNumber, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    //***ADD BILL***//
    @PutMapping(path = "/{serialNumber}/bill/{id}", produces = "application/json")
    public ResponseEntity addBillToMeasurer (@PathVariable Integer serialNumber, @PathVariable Integer id)
            throws MeasurerNotExistsException, BillNotExistsException {
        measurerService.addBillToMeasurer(serialNumber, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
