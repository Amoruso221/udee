package edu.utn.udee.Udee.repository;

import edu.utn.udee.Udee.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsernameAndPassword(String username , String password);
}
