package edu.utn.udee.Udee.controller;

import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.dto.MeterDto;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.BillNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.service.MeterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/meter")
public class MeterController {

    private final MeterService meterService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeterController(MeterService meterService, ModelMapper modelMapper) {
        this.meterService = meterService;
        this.modelMapper = modelMapper;
    }



    //***ADD NEW***//
    @PostMapping(consumes = "application/json")
    public ResponseEntity addMeter (@RequestBody MeterDto meterDto){
//        meterService.addMeter(Meter.builder().
//                        serialNumber(meterDto.getSerialNumber()).
//                        brand(meterDto.getBrand()).
//                        model(meterDto.getModel()).
//                        measurement(meterDto.getMeasurement()).
//                        build());
        Meter newMeter = meterService.addMeter(modelMapper.map(meterDto, Meter.class));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMeter.getSerialNumber())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //***GET ALL***//
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MeterDto>> getAll(Pageable pageable){
            Page page = meterService.getAll(pageable);
            return ResponseEntity.
                    status(HttpStatus.OK).
                    header("X-Total-Count", Long.toString(page.getTotalElements())).
                    header("X-Total-Pages", Long.toString(page.getTotalPages())).
                    body(page.getContent());
    }

    //***GET BY ID***//
    @GetMapping(path = "/{serialNumber}", produces = "application/json")
    public ResponseEntity<MeterDto> getBySerialNumber (@PathVariable Integer serialNumber)
            throws MeterNotExistsException {
        Meter meter = meterService.getBySerialNumber(serialNumber);
        return ResponseEntity.ok(MeterDto.from(meter));
    }

    //***DELETE BY ID***//
    @DeleteMapping(path = "/{serialNumber}", produces = "application/json")
    public ResponseEntity deleteMeter(@PathVariable Integer serialNumber)
            throws MeterNotExistsException {
        meterService.deleteBySerialNumber(serialNumber);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //***ADD MEASUREMENT***//
    @PutMapping(path = "/{serialNumber}/measurement/{idMeasurement}", produces = "application/json")
    public ResponseEntity addMeasurementToMeter (@PathVariable Integer serialNumber, @PathVariable Integer idMeasurement)
            throws MeterNotExistsException, MeasurementNotExistsException {
        meterService.addMeasurementToMeter(serialNumber, idMeasurement);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //***ADD ADDRESS***//
    @PutMapping(path = "/{serialNumber}/address/{id}", produces = "application/json")
    public ResponseEntity addAddressToMeter(@PathVariable Integer serialNumber, @PathVariable Integer id)
            throws MeterNotExistsException, AddressNotExistsException {
        meterService.addAddressToMeter(serialNumber, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    //***ADD BILL***//
    @PutMapping(path = "/{serialNumber}/bill/{id}", produces = "application/json")
    public ResponseEntity addBillToMeter (@PathVariable Integer serialNumber, @PathVariable Integer id)
            throws MeterNotExistsException, BillNotExistsException {
        meterService.addBillToMeter(serialNumber, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
