// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostLikedEvent {
    private UUID postId;
    private UUID userId;
    private LocalDateTime timestamp;
    
    public PostLikedEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public PostLikedEvent(UUID postId, UUID userId) {
        this();
        this.postId = postId;
        this.userId = userId;
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
