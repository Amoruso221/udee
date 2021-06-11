package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.domain.Measurement;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/backoffice/meter")
public class MeterController {

    private final MeterService meterService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeterController(MeterService meterService, ModelMapper modelMapper) {
        this.meterService = meterService;
        this.modelMapper = modelMapper;
    }

    private URI getLocation (Meter meter) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(meter.getSerialNumber())
                .toUri();
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
        return ResponseEntity.created(getLocation(newMeter)).build();
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
    @PutMapping(path = "/{serialNumber}/measurements/{idMeasurement}", produces = "application/json")
    public ResponseEntity addMeasurementToMeter (@PathVariable Integer serialNumber, @PathVariable Integer idMeasurement)
            throws MeterNotExistsException, MeasurementNotExistsException {
        meterService.addMeasurementToMeter(serialNumber, idMeasurement);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //***ADD ADDRESS***//
    @PutMapping(path = "/{serialNumber}/addresses/{id}", produces = "application/json")
    public ResponseEntity addAddressToMeter(@PathVariable Integer serialNumber, @PathVariable Integer id)
            throws MeterNotExistsException, AddressNotExistsException {
        meterService.addAddressToMeter(serialNumber, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    //***ADD BILL***//
//    @PutMapping(path = "/{serialNumber}/bills/{id}", produces = "application/json")
//    public ResponseEntity addBillToMeter (@PathVariable Integer serialNumber, @PathVariable Integer id)
//            throws MeterNotExistsException, BillNotExistsException {
//        meterService.addBillToMeter(serialNumber, id);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
