// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserFollowedEvent {
    private UUID followerId;
    private UUID followedId;
    private LocalDateTime timestamp;
    
    public UserFollowedEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public UserFollowedEvent(UUID followerId, UUID followedId) {
        this();
        this.followerId = followerId;
        this.followedId = followedId;
    }
    
    // Getters and Setters
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
