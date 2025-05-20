package org.example.gilbertxdheis.infrastructure;

import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.domain.Report;
import org.example.gilbertxdheis.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements CRUDRepository<User, Long> {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (username, first_name, last_name, email, password, address, postal_code, business_id, role, is_verified, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Default values for role, isVerified, and isAdmin
        String role = "ROLE_USER";
        boolean isVerified = false;
        boolean isAdmin = false;

        try {
            // Debugging: Log the user details
            System.out.println("Creating user: " + user);

            // Execute the SQL query
            int rowsAffected = jdbcTemplate.update(sql,
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAddress(),
                    user.getPostalCode(),
                    user.getBusinessId(),
                    role,
                    isVerified,
                    isAdmin
            );

            // Debugging: Log the result
            if (rowsAffected > 0) {
                System.out.println("User created successfully.");
            } else {
                System.err.println("No rows were affected. User creation failed.");
            }
        } catch (Exception e) {
            // Log and rethrow the exception
            System.err.println("Error executing SQL: " + e.getMessage());
            throw new RuntimeException("Error executing SQL: " + e.getMessage(), e);
        }
    }

    @Override
    public User read(Long id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper(), id);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, is_verified = ?, role = ? WHERE email = ?";
        String role = user.getRole() != null && !user.getRole().startsWith("ROLE_")
                ? "ROLE_" + user.getRole().toUpperCase()
                : user.getRole();
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.isVerified(), role, user.getEmail());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Iterable<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper(), id));
        } catch (Exception e) {
            System.err.println("Error loading user with ID: " + id + ". Exception: " + e.getMessage());
            return Optional.empty();
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            user.setPostalCode(rs.getString("postal_code"));
            user.setBusinessId(rs.getString("business_id"));
            user.setRole(rs.getString("role"));
            user.setVerified(rs.getBoolean("is_verified"));
            user.setAdmin(rs.getBoolean("is_admin"));
            return user;
        };
    }

    public void updateLastLogin(String email) {
        String sql = "UPDATE users SET last_login = NOW() WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper(), email));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // No user found with the given email
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("Error loading user with email: " + email + ". Exception: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
        String role = user.getRole() != null && !user.getRole().startsWith("ROLE_")
                ? "ROLE_" + user.getRole().toUpperCase()
                : user.getRole();
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), role);
    }

    public void updateUserRole(Long userId, String newRole) {
        String role = newRole != null && !newRole.startsWith("ROLE_")
                ? "ROLE_" + newRole.toUpperCase()
                : newRole;
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, role, userId);
    }

    public void saveReport(Report report) {
        String sql = "INSERT INTO reports (reporter_id, seller_id, reason, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, report.getReporterId(), report.getSellerId(), report.getReason(), report.getStatus());
    }

    public Optional<Report> findReportById(Long reportId) {
        String sql = "SELECT * FROM reports WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reportRowMapper(), reportId));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Report> reportRowMapper() {
        return (rs, rowNum) -> {
            Report report = new Report();
            report.setReportId(rs.getInt("id")); // Brug setReportId
            report.setReporterId(rs.getInt("reporter_id")); // Cast til int
            report.setSellerId(rs.getInt("seller_id")); // Cast til int
            report.setReason(rs.getString("reason"));
            report.setStatus(rs.getString("status"));
            return report;
        };
    }

    public boolean existsById(Long userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }
    public void followSeller(Long followerId, Long sellerId) {
        String sql = "INSERT INTO followers (follower_id, seller_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, followerId, sellerId);
    }

    public void unfollowSeller(Long followerId, Long sellerId) {
        String sql = "DELETE FROM followers WHERE follower_id = ? AND seller_id = ?";
        jdbcTemplate.update(sql, followerId, sellerId);
    }

    public boolean isFollowing(Long followerId, Long sellerId) {
        String sql = "SELECT COUNT(*) FROM followers WHERE follower_id = ? AND seller_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, followerId, sellerId);
        return count != null && count > 0;
    }

    public List<Long> getFollowedSellers(Long followerId) {
        String sql = "SELECT seller_id FROM followers WHERE follower_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, followerId);
    }

    public List<Item> getItemsFromFollowedSellers(Long followerId) {
        String sql = "SELECT i.* FROM items i JOIN followers f ON i.seller_id = f.seller_id WHERE f.follower_id = ? AND i.status = 'AVAILABLE'";
        return jdbcTemplate.query(sql, new Object[]{followerId}, itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {
        return (rs, rowNum) -> {
            return new Item(
                    rs.getInt("item_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("condition"),
                    rs.getString("image_url"),
                    rs.getString("category"),
                    rs.getInt("seller_id"),
                    rs.getBoolean("is_active")
            );
        };
    }
}