package com.example.post.domain.event;

import java.time.LocalDateTime;

/**
 * PostCreatedEvent - Domain Event
 * Yeni gönderi oluşturulduğunda tetiklenen domain event
 */
public class PostCreatedEvent {
    private final String postId;
    private final String userId;
    private final String caption;
    private final LocalDateTime occurredOn;

    public PostCreatedEvent(String postId, String userId, String caption) {
        this.postId = postId;
        this.userId = userId;
        this.caption = caption;
        this.occurredOn = LocalDateTime.now();
    }

    // Getters
    public String getPostId() { return postId; }
    public String getUserId() { return userId; }
    public String getCaption() { return caption; }
    public LocalDateTime getOccurredOn() { return occurredOn; }

    @Override
    public String toString() {
        return "PostCreatedEvent{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", caption='" + caption + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
