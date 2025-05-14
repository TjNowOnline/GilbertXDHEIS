package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.RoleService;
import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final JdbcUserRepository userRepository;

    public RoleController(RoleService roleService, JdbcUserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignRole(@RequestParam Long userId, @RequestParam String role) {
        boolean success = roleService.assignRoleToUser(userId, role);
        if (success) {
            return ResponseEntity.ok("Role assigned successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to assign role.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeRole(@RequestParam Long userId) {
        boolean success = roleService.removeRoleFromUser(userId);
        if (success) {
            return ResponseEntity.ok("Role removed successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to remove role.");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}