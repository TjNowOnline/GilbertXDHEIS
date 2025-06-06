package org.example.gilbertxdheis.infrastructure;

import org.example.gilbertxdheis.domain.BlogPost;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcBlogPostRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcBlogPostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(BlogPost blogPost) {
        String sql = "INSERT INTO blog_posts (title, content, created_by, timestamp, image_url) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, blogPost.getTitle(), blogPost.getContent(), blogPost.getCreatedBy(), blogPost.getTimestamp(), blogPost.getImageUrl());
    }

    public BlogPost read(Long postId) {
        String sql = "SELECT * FROM blog_posts WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new BlogPost(
                rs.getLong("post_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getInt("created_by"),
                rs.getTimestamp("timestamp"),
                rs.getString("image_url")
        ), postId);
    }

    public void update(BlogPost blogPost) {
        String sql = "UPDATE blog_posts SET title = ?, content = ?, image_url = ? WHERE post_id = ?";
        jdbcTemplate.update(sql, blogPost.getTitle(), blogPost.getContent(), blogPost.getImageUrl(), blogPost.getId());
    }

    public void delete(Long postId) {
        String sql = "DELETE FROM blog_posts WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    public List<BlogPost> findAll() {
        String sql = "SELECT * FROM blog_posts";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new BlogPost(
                rs.getLong("post_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getInt("created_by"),
                rs.getTimestamp("timestamp"),
                rs.getString("image_url")
        ));
    }

    public List<BlogPost> findRecentBlogPosts(int limit) {
        String sql = "SELECT * FROM blog_posts ORDER BY timestamp DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new BlogPost(
                rs.getLong("post_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getInt("created_by"),
                rs.getTimestamp("timestamp"),
                rs.getString("image_url")
        ), limit);
    }

    public void deleteById(Long postId) {
        String sql = "DELETE FROM blog_posts WHERE post_id = ?";
        System.out.println("Executing delete query for post_id: " + postId);
        jdbcTemplate.update(sql, postId);
    }
}