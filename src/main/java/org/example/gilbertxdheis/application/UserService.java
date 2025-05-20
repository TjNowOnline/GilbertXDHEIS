package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JdbcUserRepository userRepository;

    public UserService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        System.out.println("createUser method called");
        userRepository.create(user);
        System.out.println(user);
    }
}
