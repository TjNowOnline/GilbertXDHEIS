package org.example.gilbertxdheis.infrastructure;

import org.example.gilbertxdheis.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcItemRepository implements CRUDRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public JdbcItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Item> itemRowMapper = new RowMapper<>() {
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

    @Override
    public void create(Object entity) {
        Item item = (Item) entity;
        String sql = "INSERT INTO items (title, description, price, `condition`, image_url, category, seller_id, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCondition(),
                item.getImageUrl(),
                item.getCategory(),
                item.getSellerId(),
                item.isActive()
        );
    }

    @Override
    public Object read(Object id) {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, itemRowMapper);
    }

    @Override
    public void update(Object entity) {
        Item item = (Item) entity;
        String sql = "UPDATE items SET title = ?, description = ?, price = ?, `condition` = ?, image_url = ?, category = ?, seller_id = ?, is_active = ? WHERE item_id = ?";
        jdbcTemplate.update(sql,
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCondition(),
                item.getImageUrl(),
                item.getCategory(),
                item.getSellerId(),
                item.isActive(),
                item.getItemId()
        );
    }

    @Override
    public void delete(Object entity) {
        Item item = (Item) entity;
        String sql = "DELETE FROM items WHERE item_id = ?";
        jdbcTemplate.update(sql, item.getItemId());
    }

    @Override
    public Iterable<Item> findAll() {
        String sql = "SELECT * FROM items";
        List<Item> items = jdbcTemplate.query(sql, itemRowMapper);
        return items;
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

    public Optional<Item> findById(Long id) {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, itemRowMapper(), id));
        } catch (Exception e) {
            System.err.println("Error loading item with ID: " + id + ". Exception: " + e.getMessage());
            return Optional.empty();
        }
    }
}
