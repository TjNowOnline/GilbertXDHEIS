package org.example.gilbertxdheis.application;

import jakarta.validation.Valid;
import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JdbcUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(JdbcUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user) {
        System.out.println("createUser method called");

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.create(user);
        System.out.println(user);
    }

    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String email = ((org.springframework.security.core.userdetails.User) principal).getUsername(); // Username here represents the email
            System.out.println("Fetching user with email: " + email); // Debug log

            return userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        System.err.println("User not found with email: " + email); // Error log
                        return new IllegalArgumentException("User not found: " + email);
                    });
        } else if (principal instanceof String) {
            throw new IllegalArgumentException("Anonymous user is not logged in.");
        } else {
            throw new IllegalArgumentException("Unexpected principal type: " + principal.getClass().getName());
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    public void updateUser(String username, User updatedUser) {
        // Fetch the existing user by email (username in this case)
        User existingUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        // Update user details from the form
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPostalCode(updatedUser.getPostalCode());
        existingUser.setBusinessId(updatedUser.getBusinessId());

        // Update password only if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Save the updated user to the database
        userRepository.update(existingUser);
    }
}
