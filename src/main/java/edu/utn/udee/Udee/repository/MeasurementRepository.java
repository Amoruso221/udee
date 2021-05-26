package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {

    Page<Measurement> findAll(Pageable pageable);
}