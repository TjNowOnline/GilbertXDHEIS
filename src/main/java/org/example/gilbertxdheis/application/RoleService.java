package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final JdbcUserRepository userRepository;

    public RoleService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean assignRoleToUser(Long userId, String role) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        user.setRole(role);
        userRepository.update(user);
        return true;
    }

    @Transactional
    public boolean removeRoleFromUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        user.setRole(null);
        userRepository.update(user);
        return true;
    }
}