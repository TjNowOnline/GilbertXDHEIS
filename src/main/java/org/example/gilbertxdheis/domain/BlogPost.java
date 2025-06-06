package org.example.gilbertxdheis.domain;

import java.util.Date;

public class BlogPost {
    private Long id;
    private String title;
    private String content;
    private int createdBy;
    private Date timestamp;
    private String imageUrl;

    public BlogPost(Long Id, String title, String content, int createdBy, Date timestamp, String imageUrl) {
        this.id = Id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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