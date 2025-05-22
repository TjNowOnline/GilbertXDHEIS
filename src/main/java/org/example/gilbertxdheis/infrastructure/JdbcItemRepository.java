package org.example.gilbertxdheis.infrastructure;

import org.example.gilbertxdheis.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcItemRepository implements CRUDRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public JdbcItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Object entity) {
    }

    @Override
    public Object read(Object o) {
        return null;
    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public Iterable findAll() {
        return null;
    }


    public List<Item> findItemsBySellerIdAndStatus(Long userId, String status) {
        String sql = "SELECT * FROM items WHERE seller_id = ? AND is_active = ?";
        return jdbcTemplate.query(sql, new Object[]{userId, status.equals("FOR_SALE")}, itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {
        return new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
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
            }
        };
    }
}
