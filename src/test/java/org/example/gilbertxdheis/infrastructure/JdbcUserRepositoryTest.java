package org.example.gilbertxdheis.infrastructure;

import org.example.gilbertxdheis.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JdbcUserRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcUserRepository jdbcUserRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setUsername("testuser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setAddress("123 Test St");
        user.setPostalCode("12345");
        user.setBusinessId("B123");

        String sql = "INSERT INTO users (username, first_name, last_name, email, password, address, postal_code, business_id, role, is_verified, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        when(jdbcTemplate.update(eq(sql), any(Object[].class))).thenReturn(1);

        jdbcUserRepository.create(user);

        verify(jdbcTemplate, times(1)).update(eq(sql), any(Object[].class));
    }

    @Test
    void testRead() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId.intValue());
        user.setUsername("testuser");

        String sql = "SELECT * FROM users WHERE user_id = ?";

        when(jdbcTemplate.queryForObject(eq(sql), any(RowMapper.class), eq(userId))).thenReturn(user);

        User result = jdbcUserRepository.read(userId);

        assertNotNull(result);
        assertEquals(userId.intValue(), result.getUserId());
        verify(jdbcTemplate, times(1)).queryForObject(eq(sql), any(RowMapper.class), eq(userId));
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setUsername("updateduser");
        user.setFirstName("Updated");
        user.setLastName("User");
        user.setEmail("updated@example.com");
        user.setPassword("newpassword");
        user.setAddress("456 Updated St");
        user.setPostalCode("67890");
        user.setBusinessId("B456");

        String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ?, email = ?, password = ?, address = ?, postal_code = ?, business_id = ? WHERE email = ?";

        jdbcUserRepository.update(user);

        verify(jdbcTemplate, times(1)).update(eq(sql),
                eq(user.getUsername()),
                eq(user.getFirstName()),
                eq(user.getLastName()),
                eq(user.getEmail()),
                eq(user.getPassword()),
                eq(user.getAddress()),
                eq(user.getPostalCode()),
                eq(user.getBusinessId()),
                eq(user.getEmail()));
    }

    @Test
    void testDelete() {
        Long userId = 1L;

        String sql = "DELETE FROM users WHERE user_id = ?";

        jdbcUserRepository.delete(userId);

        verify(jdbcTemplate, times(1)).update(eq(sql), eq(userId));
    }

    @Test
    void testFindAll() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");

        String sql = "SELECT * FROM users";

        when(jdbcTemplate.query(eq(sql), any(RowMapper.class))).thenReturn(Arrays.asList(user1, user2));

        Iterable<User> result = jdbcUserRepository.findAll();

        assertNotNull(result);
        List<User> userList = (List<User>) result;
        assertEquals(2, userList.size());
        verify(jdbcTemplate, times(1)).query(eq(sql), any(RowMapper.class));
    }
}