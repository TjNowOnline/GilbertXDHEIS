package org.example.gilbertxdheis.domain;

import java.util.Date;

public class BlogPost {
    private int postId;
    private String title;
    private String content;
    private int createdBy;
    private Date timestamp;
    private String imageUrl;

    public BlogPost(int postId, String title, String content, int createdBy, Date timestamp, String imageUrl) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}