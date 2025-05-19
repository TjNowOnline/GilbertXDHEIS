package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final JdbcUserRepository userRepository;

    public FollowService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void followSeller(Long followerId, Long sellerId) {
        // Validering
        if (followerId == null || sellerId == null) {
            throw new IllegalArgumentException("Follower ID og Seller ID må ikke være null");
        }
        if (followerId.equals(sellerId)) {
            throw new IllegalArgumentException("Du kan ikke følge dig selv");
        }
        if (!userRepository.existsById(followerId)) {
            throw new IllegalArgumentException("Bruger med ID " + followerId + " findes ikke");
        }
        if (!userRepository.existsById(sellerId)) {
            throw new IllegalArgumentException("Sælger med ID " + sellerId + " findes ikke");
        }
        if (userRepository.isFollowing(followerId, sellerId)) {
            throw new IllegalStateException("Du følger allerede denne sælger");
        }

        userRepository.followSeller(followerId, sellerId);
    }

    public void unfollowSeller(Long followerId, Long sellerId) {
        if (followerId == null || sellerId == null) {
            throw new IllegalArgumentException("Follower ID og Seller ID må ikke være null");
        }
        if (!userRepository.isFollowing(followerId, sellerId)) {
            throw new IllegalStateException("Du følger ikke denne sælger");
        }

        userRepository.unfollowSeller(followerId, sellerId);
    }

    public boolean isFollowing(Long followerId, Long sellerId) {
        return userRepository.isFollowing(followerId, sellerId);
    }
}