package edu.utn.udee.Udee.repository;


import edu.utn.udee.Udee.domain.Measurer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurerRepository extends CrudRepository<Measurer, Integer>{

    Page<Measurer> findAll(Pageable pageable);

}
