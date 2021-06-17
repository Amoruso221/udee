package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.Measurement;
import org.springframework.data.repository.CrudRepository;

public interface ClientMeasurementRepository extends CrudRepository<Measurement,Integer> {
}
