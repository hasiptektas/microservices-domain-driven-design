package com.example.post.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Comment Entity
 * Yorum domain nesnesini temsil eder
 */
public class Comment {
    private final String commentId;
    private final String postId;
    private final String userId;
    private final String content;
    private final LocalDateTime createdAt;

    public Comment(String postId, String userId, String content) {
        this.commentId = UUID.randomUUID().toString();
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public String getCommentId() { return commentId; }
    public String getPostId() { return postId; }
    public String getUserId() { return userId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Comment comment = (Comment) obj;
        return commentId.equals(comment.commentId);
    }

    @Override
    public int hashCode() {
        return commentId.hashCode();
    }
}
