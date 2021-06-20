package edu.utn.udee.Udee.controller.backoffice;

import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.dto.MeasurementDto;
import edu.utn.udee.Udee.exceptions.AddressNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeterIsRequiredException;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.service.backoffice.AddressService;
import edu.utn.udee.Udee.service.backoffice.MeasurementService;
import edu.utn.udee.Udee.service.backoffice.MeterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/backoffice/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeterService meterService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeterService meterService, AddressService addressService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.meterService = meterService;
        this.addressService = addressService;
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
            throws MeterNotExistsException, MeterIsRequiredException {
        if (measurementDto.getMeter() == null)
            throw new MeterIsRequiredException();
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
                status(page.getTotalElements() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).
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

    //***GET BY ADDRESS AND DATETIME RANGE***//
    @GetMapping(path = "/addresses/{idAddress}/{beginDateTime}/{endDateTime}", produces = "application/json")
    public  ResponseEntity<List<MeasurementDto>> getByAddressAndDateTimeRange(@PathVariable Integer idAddress, @PathVariable LocalDateTime beginDateTime, @PathVariable LocalDateTime endDateTime)
            throws AddressNotExistsException {
        Address address = addressService.findAddressById(idAddress);
        Meter meter = meterService.getByAddress(address);
        List<Measurement> filteredMeasurements = measurementService.getByMeterAndDateTimeRange(meter.getSerialNumber(), beginDateTime, endDateTime);
        List<MeasurementDto> filteredMeasurementsDto = listMeasurementsToDto(filteredMeasurements);
        return ResponseEntity.
                status(filteredMeasurementsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).
                body(filteredMeasurementsDto);
    }

    //***DELETE BY ID***//
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteMeasurement(@PathVariable Integer id)
            throws MeterNotExistsException {
        measurementService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    private List<MeasurementDto> listMeasurementsToDto (List<Measurement> list){
        return list.stream().
                map(x -> modelMapper.map(x, MeasurementDto.class)).
                collect(Collectors.toList());
    }

}
