package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.repository.MeasurerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.utn.udee.Udee.domain.Measurer;

@Service
public class MeasurerService {

    private final MeasurerRepository measurerRepository;

    @Autowired
    public MeasurerService(MeasurerRepository measurerRepository) {
        this.measurerRepository = measurerRepository;
    }



    public void addMeasurer(Measurer measurer) {
        measurerRepository.save(measurer);
    }

}
