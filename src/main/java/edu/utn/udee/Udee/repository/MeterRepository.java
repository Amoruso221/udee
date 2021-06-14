package edu.utn.udee.Udee.repository;


import edu.utn.udee.Udee.domain.Address;
import edu.utn.udee.Udee.domain.Meter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends CrudRepository<Meter, Integer>{

    Page<Meter> findAll(Pageable pageable);

//    @Query(
//            value = "SELECT * FROM USERS u WHERE u.status = 1",
//            nativeQuery = true)

//SELECT column_name(s)
//    FROM table1
//    INNER JOIN table2
//    ON table1.column_name = table2.column_name
    Meter findByAddress(Address address);

}
