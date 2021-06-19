package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends CrudRepository<Bill,Integer> {

    Page<Bill> findAll (Pageable pageable);

//    List<Bill> findByAddress(String address);

    @Query(value = "select * from bills b where b.address = ?1 and b.paid = false", nativeQuery = true)
    List<Bill> findUnpaidByAddress(String address);
}
