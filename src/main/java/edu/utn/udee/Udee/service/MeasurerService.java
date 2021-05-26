package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.Measurement;
import edu.utn.udee.Udee.exceptions.MeasurementNotExistsException;
import edu.utn.udee.Udee.exceptions.MeasurerNotExistsException;
import edu.utn.udee.Udee.repository.MeasurerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.utn.udee.Udee.domain.Measurer;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class MeasurerService {

    private final MeasurerRepository measurerRepository;
    private final MeasurementService measurementService;

    @Autowired
    public MeasurerService(MeasurerRepository measurerRepository, MeasurementService measurementService) {
        this.measurerRepository = measurerRepository;
        this.measurementService = measurementService;
    }



    public void addMeasurer(Measurer measurer) {
        measurer.setMeasurement(0.0);
        measurerRepository.save(measurer);
    }

    public Page<Measurer> getAll(Pageable pageable) {
        return measurerRepository.findAll(pageable);
    }

    public Measurer getBySerialNumber(Integer serialNumber)
            throws MeasurerNotExistsException {
        return measurerRepository.findById(serialNumber).
                orElseThrow(MeasurerNotExistsException::new);
    }

    public void deleteBySerialNumber(Integer serialNumber) {
        measurerRepository.deleteById(serialNumber);
    }

    public void addMeasurementToMeasurer(Integer serialNumber, Integer idMeasurement)
            throws MeasurerNotExistsException, MeasurementNotExistsException {
        if (measurerRepository.existsById(serialNumber)) {
            Measurer measurer = this.getBySerialNumber(serialNumber);
            Measurement measurement = measurementService.getById(idMeasurement);
            measurer.setMeasurement(measurer.getMeasurement() + measurement.getKwh());
            measurement.setMeasurer(measurer);
            measurer.getMeasurements().add(measurement);
            measurerRepository.save(measurer);
        }
        else throw new MeasurerNotExistsException();
    }
}
