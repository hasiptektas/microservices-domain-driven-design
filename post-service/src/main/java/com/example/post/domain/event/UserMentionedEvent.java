package com.example.post.domain.event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserMentionedEvent - Domain Event
 * Gönderi içinde kullanıcı etiketlendiğinde tetiklenen domain event
 */
public class UserMentionedEvent {
    private final String postId;
    private final String mentioningUserId;
    private final List<String> mentionedUserIds;
    private final String caption;
    private final LocalDateTime occurredOn;

    public UserMentionedEvent(String postId, String mentioningUserId, List<String> mentionedUserIds, String caption) {
        this.postId = postId;
        this.mentioningUserId = mentioningUserId;
        this.mentionedUserIds = mentionedUserIds;
        this.caption = caption;
        this.occurredOn = LocalDateTime.now();
    }

    // Getters
    public String getPostId() { return postId; }
    public String getMentioningUserId() { return mentioningUserId; }
    public List<String> getMentionedUserIds() { return mentionedUserIds; }
    public String getCaption() { return caption; }
    public LocalDateTime getOccurredOn() { return occurredOn; }

    @Override
    public String toString() {
        return "UserMentionedEvent{" +
                "postId='" + postId + '\'' +
                ", mentioningUserId='" + mentioningUserId + '\'' +
                ", mentionedUserIds=" + mentionedUserIds +
                ", caption='" + caption + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
