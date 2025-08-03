package com.example.post.domain.model;

import com.example.post.domain.event.PostCreatedEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Post Entity - Aggregate Root
 * Gönderi domain nesnesini temsil eder
 */
public class Post {
    private final String postId;
    private final String userId;
    private String caption;
    private final MediaFile mediaFile;
    private final LocalDateTime createdAt;
    private final List<Comment> comments;
    private final List<String> domainEvents;

    public Post(String userId, String caption, MediaFile mediaFile) {
        this.postId = UUID.randomUUID().toString();
        this.userId = userId;
        this.caption = caption;
        this.mediaFile = mediaFile;
        this.createdAt = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.domainEvents = new ArrayList<>();
        
        // Domain event tetikleme
        addDomainEvent(new PostCreatedEvent(this.postId, this.userId, this.caption));
    }

    // Aggregate içinde yorum ekleme
    public void addComment(String userId, String content) {
        Comment comment = new Comment(this.postId, userId, content);
        this.comments.add(comment);
    }

    // Caption güncelleme
    public void updateCaption(String newCaption) {
        this.caption = newCaption;
    }

    private void addDomainEvent(PostCreatedEvent event) {
        // Event'i serileştirip domain events listesine ekle
        this.domainEvents.add(event.toString());
    }

    // Getters
    public String getPostId() { return postId; }
    public String getUserId() { return userId; }
    public String getCaption() { return caption; }
    public MediaFile getMediaFile() { return mediaFile; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Comment> getComments() { return new ArrayList<>(comments); }
    public List<String> getDomainEvents() { return new ArrayList<>(domainEvents); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Post post = (Post) obj;
        return postId.equals(post.postId);
    }

    @Override
    public int hashCode() {
        return postId.hashCode();
    }
}
