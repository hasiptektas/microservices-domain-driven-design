// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.feed.repository;

import com.example.feed.domain.model.FeedItem;
import java.util.List;

public interface FeedRepository {
    
    // Cache operations
    List<FeedItem> getUserFeedFromCache(String userId);
    void cacheUserFeed(String userId, List<FeedItem> feed);
    void clearUserFeedCache(String userId);
    
    // Feed queries
    List<FeedItem> getTrendingFeed();
    List<FeedItem> getExploreFeed();
    List<FeedItem> getFeedByHashtag(String hashtag);
    List<FeedItem> getFeedByUser(String targetUserId, String currentUserId);
    
    // Post operations
    void addPostToFeed(String postId, String userId);
    void removePostFromFeed(String postId, String userId);
    void updatePostInFeed(String postId, String userId);
    
    // Interaction updates
    void updateLikeCount(String postId, int likeCount);
    void updateCommentCount(String postId, int commentCount);
    void updateShareCount(String postId, int shareCount);
    
    // User relationship updates
    void addUserToFollowing(String userId, String targetUserId);
    void removeUserFromFollowing(String userId, String targetUserId);
} 