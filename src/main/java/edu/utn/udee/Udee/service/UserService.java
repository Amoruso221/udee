package edu.utn.udee.Udee.service;

import edu.utn.udee.Udee.domain.User;
import edu.utn.udee.Udee.dto.UserDto;
import edu.utn.udee.Udee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public Integer getClientDni(Authentication auth) {
        UserDto user = (UserDto) auth.getPrincipal();

        return user.getClientDto().getDni();
    }
}
