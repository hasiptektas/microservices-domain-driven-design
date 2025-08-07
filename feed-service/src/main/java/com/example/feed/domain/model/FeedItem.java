// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.feed.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class FeedItem {
    private String postId;
    private String userId;
    private String username;
    private String caption;
    private String mediaUrl;
    private String mediaType;
    private int likeCount;
    private int commentCount;
    private int shareCount;
    private LocalDateTime createdAt;
    private List<String> hashtags;
    private List<String> mentions;
    private boolean isLikedByCurrentUser;
    private boolean isSavedByCurrentUser;

    public FeedItem(String postId, String userId, String username, String caption, 
                   String mediaUrl, String mediaType, int likeCount, int commentCount, 
                   int shareCount, LocalDateTime createdAt, List<String> hashtags, 
                   List<String> mentions, boolean isLikedByCurrentUser, boolean isSavedByCurrentUser) {
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.caption = caption;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.createdAt = createdAt;
        this.hashtags = hashtags;
        this.mentions = mentions;
        this.isLikedByCurrentUser = isLikedByCurrentUser;
        this.isSavedByCurrentUser = isSavedByCurrentUser;
    }

    // Getters
    public String getPostId() { return postId; }
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getCaption() { return caption; }
    public String getMediaUrl() { return mediaUrl; }
    public String getMediaType() { return mediaType; }
    public int getLikeCount() { return likeCount; }
    public int getCommentCount() { return commentCount; }
    public int getShareCount() { return shareCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<String> getHashtags() { return hashtags; }
    public List<String> getMentions() { return mentions; }
    public boolean isLikedByCurrentUser() { return isLikedByCurrentUser; }
    public boolean isSavedByCurrentUser() { return isSavedByCurrentUser; }

    // Setters
    public void setPostId(String postId) { this.postId = postId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setCaption(String caption) { this.caption = caption; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }
    public void setShareCount(int shareCount) { this.shareCount = shareCount; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }
    public void setMentions(List<String> mentions) { this.mentions = mentions; }
    public void setLikedByCurrentUser(boolean likedByCurrentUser) { isLikedByCurrentUser = likedByCurrentUser; }
    public void setSavedByCurrentUser(boolean savedByCurrentUser) { isSavedByCurrentUser = savedByCurrentUser; }

    @Override
    public String toString() {
        return "FeedItem{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", caption='" + caption + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", likeCount=" + likeCount +
                ", commentCount=" + commentCount +
                ", shareCount=" + shareCount +
                ", createdAt=" + createdAt +
                ", hashtags=" + hashtags +
                ", mentions=" + mentions +
                ", isLikedByCurrentUser=" + isLikedByCurrentUser +
                ", isSavedByCurrentUser=" + isSavedByCurrentUser +
                '}';
    }
} 