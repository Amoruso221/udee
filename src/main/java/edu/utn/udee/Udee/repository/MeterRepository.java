package edu.utn.udee.Udee.repository;


import edu.utn.udee.Udee.domain.Meter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends CrudRepository<Meter, Integer>{

    Page<Meter> findAll(Pageable pageable);

}
