// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.application;

import com.example.interaction.domain.model.Like;
import com.example.interaction.domain.model.Share;
import com.example.interaction.domain.model.Follow;
import com.example.interaction.domain.service.InteractionService;
import com.example.interaction.domain.event.PostLikedEvent;
import com.example.interaction.domain.event.PostSharedEvent;
import com.example.interaction.domain.event.UserFollowedEvent;
import com.example.interaction.infrastructure.EventPublisher;
import java.util.List;
import java.util.UUID;

public class InteractionCommandHandler {
    private final InteractionService interactionService;
    private final EventPublisher eventPublisher;
    
    public InteractionCommandHandler(InteractionService interactionService, EventPublisher eventPublisher) {
        this.interactionService = interactionService;
        this.eventPublisher = eventPublisher;
    }
    
    public void handleLikePost(UUID userId, UUID postId) {
        Like like = interactionService.likePost(userId, postId);
        
        PostLikedEvent event = new PostLikedEvent(postId, userId);
        eventPublisher.publish("post.liked", event);
    }
    
    public void handleUnlikePost(UUID userId, UUID postId) {
        interactionService.unlikePost(userId, postId);
        
        // Could publish PostUnlikedEvent if needed
    }
    
    public void handleSharePost(UUID userId, UUID postId, String shareMessage) {
        Share share = interactionService.sharePost(userId, postId, shareMessage);
        
        PostSharedEvent event = new PostSharedEvent(postId, userId, shareMessage);
        eventPublisher.publish("post.shared", event);
    }
    
    public void handleFollowUser(UUID followerId, UUID followedId) {
        Follow follow = interactionService.followUser(followerId, followedId);
        
        UserFollowedEvent event = new UserFollowedEvent(followerId, followedId);
        eventPublisher.publish("user.followed", event);
    }
    
    public void handleUnfollowUser(UUID followerId, UUID followedId) {
        interactionService.unfollowUser(followerId, followedId);
        
        // Could publish UserUnfollowedEvent if needed
    }
    
    // Query methods
    public boolean hasUserLikedPost(UUID userId, UUID postId) {
        return interactionService.hasUserLikedPost(userId, postId);
    }
    
    public long getPostLikesCount(UUID postId) {
        return interactionService.getPostLikesCount(postId);
    }
    
    public List<Like> getPostLikes(UUID postId) {
        return interactionService.getPostLikes(postId);
    }
    
    public long getPostSharesCount(UUID postId) {
        return interactionService.getPostSharesCount(postId);
    }
    
    public List<Share> getPostShares(UUID postId) {
        return interactionService.getPostShares(postId);
    }
    
    public boolean isUserFollowing(UUID followerId, UUID followedId) {
        return interactionService.isUserFollowing(followerId, followedId);
    }
    
    public long getFollowersCount(UUID userId) {
        return interactionService.getFollowersCount(userId);
    }
    
    public long getFollowingCount(UUID userId) {
        return interactionService.getFollowingCount(userId);
    }
    
    public List<Follow> getUserFollowers(UUID userId) {
        return interactionService.getUserFollowers(userId);
    }
    
    public List<Follow> getUserFollowing(UUID userId) {
        return interactionService.getUserFollowing(userId);
    }
}
