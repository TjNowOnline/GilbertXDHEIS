package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.Profile;
import org.example.gilbertxdheis.domain.SellerStats;
import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private JdbcUserRepository jdbcUserRepository;

    public boolean verifyAndAssignSellerRole(Long userId) {
        Optional<User> user = jdbcUserRepository.findById(userId);
        if (user.isPresent() && "ROLE_USER".equals(user.get().getRole())) {
            jdbcUserRepository.updateUserRole(userId, "seller");
            return true;
        }
        return false;
    }

    public Optional<Profile> getProfileByUserId(Long userId) {
        Optional<User> user = jdbcUserRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            Profile profile = new Profile();
            profile.setUserId(foundUser.getUserId());
            profile.setUsername(foundUser.getUsername());
            profile.setEmail(foundUser.getEmail());
            profile.setRole(foundUser.getRole());
            profile.setBio("This seller offers premium pre-owned goods.");
            profile.setProfilePicture("https://via.placeholder.com/150");
            profile.setItemsSold(profile.getItemsSold());
            profile.setFollowers(profile.getFollowers());
            return Optional.of(profile);
        }
        return Optional.empty();
    }

    public SellerStats getSellerStats(Long userId) {
        Optional<User> user = jdbcUserRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            SellerStats sellerStats = new SellerStats();
            return sellerStats;
        }
        return null;
    }

    public SellerStats getSellerStatsByUserId(Long userId) {
        return getSellerStats(userId);
    }
}