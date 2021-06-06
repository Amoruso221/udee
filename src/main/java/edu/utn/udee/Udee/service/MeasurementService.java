package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurerNotExistsException;
import edu.utn.udee.Udee.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }



    public Measurement addMeasurement(Measurement measurement) {
        measurement.setDateTime(LocalDateTime.now());
        if (measurement.getMeasurer().getSerialNumber() != null)
            measurement.getMeasurer().setMeasurement(measurement.getMeasurer().getMeasurement() + measurement.getKwh());
        return measurementRepository.save(measurement);
    }

    public Page<Measurement> getAll(Pageable pageable) {
        return measurementRepository.findAll(pageable);
    }

    public Measurement getById(Integer id)
            throws MeasurementNotExistsException {
        return measurementRepository.findById(id).
                orElseThrow(MeasurementNotExistsException::new);
    }

    public void deleteById(Integer id)
            throws MeasurerNotExistsException{
        if (measurementRepository.existsById(id))
            measurementRepository.deleteById(id);
        else throw new MeasurerNotExistsException();
    }
}
