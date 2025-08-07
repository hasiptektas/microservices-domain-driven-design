// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Follow {
    private UUID id;
    private UUID followerId;
    private UUID followedId;
    private LocalDateTime createdAt;
    
    public Follow() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
    
    public Follow(UUID followerId, UUID followedId) {
        this();
        this.followerId = followerId;
        this.followedId = followedId;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getFollowerId() {
        return followerId;
    }
    
    public void setFollowerId(UUID followerId) {
        this.followerId = followerId;
    }
    
    public UUID getFollowedId() {
        return followedId;
    }
    
    public void setFollowedId(UUID followedId) {
        this.followedId = followedId;
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
        Follow follow = (Follow) o;
        return id.equals(follow.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
