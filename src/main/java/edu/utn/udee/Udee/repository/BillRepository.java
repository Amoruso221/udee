package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill,Integer> {

    Page<Bill> findAll (Pageable pageable);
}
