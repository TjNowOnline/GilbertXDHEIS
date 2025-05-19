package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerStatsService {

    @Autowired
    private JdbcUserRepository UserRepository;

    public Optional<User> getSellerStats(Long userId) {
        return UserRepository.findById(userId);
    }
}