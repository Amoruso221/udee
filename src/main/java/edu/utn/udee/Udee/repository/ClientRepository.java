package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    Boolean existsByDni(Integer dni);
    Client getByDni(Integer dni);
    Page<Client> findAll(Pageable pageable);
<<<<<<< HEAD
    Client findByUser(User user);

    @Query(value = "select * from users s", nativeQuery = true)
    List<Client> findTenMoreConsumersByDateTimeRange();

=======
>>>>>>> a0457bc5c2059bc66bd06bcc2e5c3dc163d285a6
}
