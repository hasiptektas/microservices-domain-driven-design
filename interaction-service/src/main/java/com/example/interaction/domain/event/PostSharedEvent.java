// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostSharedEvent {
    private UUID postId;
    private UUID userId;
    private String shareMessage;
    private LocalDateTime timestamp;
    
    public PostSharedEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public PostSharedEvent(UUID postId, UUID userId, String shareMessage) {
        this();
        this.postId = postId;
        this.userId = userId;
        this.shareMessage = shareMessage;
    }
    
    // Getters and Setters
    public UUID getPostId() {
        return postId;
    }
    
    public void setPostId(UUID postId) {
        this.postId = postId;
    }
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public String getShareMessage() {
        return shareMessage;
    }
    
    public void setShareMessage(String shareMessage) {
        this.shareMessage = shareMessage;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
