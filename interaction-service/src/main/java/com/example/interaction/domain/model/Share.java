// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Share {
    private UUID id;
    private UUID userId;
    private UUID postId;
    private String shareMessage;
    private LocalDateTime createdAt;
    
    public Share() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
    
    public Share(UUID userId, UUID postId, String shareMessage) {
        this();
        this.userId = userId;
        this.postId = postId;
        this.shareMessage = shareMessage;
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
    
    public String getShareMessage() {
        return shareMessage;
    }
    
    public void setShareMessage(String shareMessage) {
        this.shareMessage = shareMessage;
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
        Share share = (Share) o;
        return id.equals(share.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
