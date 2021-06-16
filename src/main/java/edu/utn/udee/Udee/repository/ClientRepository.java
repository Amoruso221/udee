package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.Client;
import edu.utn.udee.Udee.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    Boolean existsByDni(Integer dni);
    Client getByDni(Integer dni);
    Page<Client> findAll(Pageable pageable);
    Client findByUser(User user);
}
