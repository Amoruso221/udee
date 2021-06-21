package edu.utn.udee.Udee.controller.receiver;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.domain.Meter;
import edu.utn.udee.Udee.exceptions.MeterNotExistsException;
import edu.utn.udee.Udee.service.backoffice.MeasurementService;
import edu.utn.udee.Udee.service.backoffice.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/receiver")
public class ReceiverMeasurementController {

    private final MeasurementService measurementService;
    private final MeterService meterService;

    @Autowired
    public ReceiverMeasurementController(MeasurementService measurementService, MeterService meterService) {
        this.measurementService = measurementService;
        this.meterService = meterService;
    }


    private static class MeasurerReceiverDto{
        Double kwh;
        Integer serialNumber;
        LocalDateTime dateTime;
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity receiveMeasurer (@RequestBody MeasurerReceiverDto measurerReceiverDto)
            throws MeterNotExistsException {
        Meter meter = meterService.getBySerialNumber(measurerReceiverDto.serialNumber);
        Measurement newMeasurement = measurementService.addMeasurement(Measurement.builder().
                idMeasurement(0).
                kwh(measurerReceiverDto.kwh).
                dateTime(measurerReceiverDto.dateTime).
                meter(meter).
                build());
        return ResponseEntity.created(getLocation(newMeasurement)).build();
    }

    private URI getLocation (Measurement measurement) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(measurement.getIdMeasurement())
                .toUri();
    }
}
