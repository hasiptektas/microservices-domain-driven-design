// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.tagging.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Tag {
    private String tagId;
    private String name;
    private String type; // HASHTAG, MENTION, LOCATION
    private String postId;
    private String userId;
    private LocalDateTime createdAt;
    private int usageCount;

    public Tag(String name, String type, String postId, String userId) {
        this.tagId = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.usageCount = 1;
    }

    // Getters
    public String getTagId() { return tagId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getPostId() { return postId; }
    public String getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getUsageCount() { return usageCount; }

    // Setters
    public void setTagId(String tagId) { this.tagId = tagId; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setPostId(String postId) { this.postId = postId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUsageCount(int usageCount) { this.usageCount = usageCount; }

    public void incrementUsageCount() {
        this.usageCount++;
    }

    public boolean isHashtag() {
        return "HASHTAG".equals(type);
    }

    public boolean isMention() {
        return "MENTION".equals(type);
    }

    public boolean isLocation() {
        return "LOCATION".equals(type);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId='" + tagId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", usageCount=" + usageCount +
                '}';
    }
} 