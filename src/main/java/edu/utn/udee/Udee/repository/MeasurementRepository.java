package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {

    Page<Measurement> findAll(Pageable pageable);

    @Query(value = "select * from measurements m " +
            "where ms.measurer_serial_number = ?1" +
            "and m.dateTime between ?2 and ?3;",
            nativeQuery = true)
    List<Measurement> findByMeterAndDateTimeRange(Integer meterSerialNumber, LocalDateTime beginDateTime, LocalDateTime endDateTime);
}
