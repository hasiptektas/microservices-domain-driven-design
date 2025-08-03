package com.example.interaction.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Like {
    private UUID id;
    private UUID userId;
    private UUID postId;
    private LocalDateTime createdAt;
    
    public Like() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
    
    public Like(UUID userId, UUID postId) {
        this();
        this.userId = userId;
        this.postId = postId;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public UUID getPostId() {
        return postId;
    }
    
    public void setPostId(UUID postId) {
        this.postId = postId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return id.equals(like.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
