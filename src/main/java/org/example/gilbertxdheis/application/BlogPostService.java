package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.BlogPost;
import org.example.gilbertxdheis.infrastructure.JdbcBlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    @Autowired
    private final JdbcBlogPostRepository blogPostRepository;

    public BlogPostService(JdbcBlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public void createBlogPost(BlogPost blogPost) {
        blogPostRepository.create(blogPost);
    }

    public BlogPost getBlogPostById(Long postId) {
        return blogPostRepository.read(postId);
    }

    public void updateBlogPost(BlogPost blogPost) {
        blogPostRepository.update(blogPost);
    }

    public void deleteBlogPost(Long postId) {
        blogPostRepository.delete(postId);
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    public List<BlogPost> getRecentBlogPosts(int limit) {
        return blogPostRepository.findRecentBlogPosts(limit);
    }
}