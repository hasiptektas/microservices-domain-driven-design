package com.example.interaction.domain.service;

import com.example.interaction.domain.model.Like;
import com.example.interaction.domain.model.Share;
import com.example.interaction.domain.model.Follow;
import com.example.interaction.repository.LikeRepository;
import com.example.interaction.repository.ShareRepository;
import com.example.interaction.repository.FollowRepository;
import java.util.List;
import java.util.UUID;

public class InteractionService {
    private final LikeRepository likeRepository;
    private final ShareRepository shareRepository;
    private final FollowRepository followRepository;
    
    public InteractionService(LikeRepository likeRepository, 
                            ShareRepository shareRepository,
                            FollowRepository followRepository) {
        this.likeRepository = likeRepository;
        this.shareRepository = shareRepository;
        this.followRepository = followRepository;
    }
    
    // Like operations
    public Like likePost(UUID userId, UUID postId) {
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalStateException("User already liked this post");
        }
        
        Like like = new Like(userId, postId);
        return likeRepository.save(like);
    }
    
    public void unlikePost(UUID userId, UUID postId) {
        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }
    
    public boolean hasUserLikedPost(UUID userId, UUID postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
    
    public long getPostLikesCount(UUID postId) {
        return likeRepository.countByPostId(postId);
    }
    
    public List<Like> getPostLikes(UUID postId) {
        return likeRepository.findByPostId(postId);
    }
    
    // Share operations
    public Share sharePost(UUID userId, UUID postId, String shareMessage) {
        Share share = new Share(userId, postId, shareMessage);
        return shareRepository.save(share);
    }
    
    public long getPostSharesCount(UUID postId) {
        return shareRepository.countByPostId(postId);
    }
    
    public List<Share> getPostShares(UUID postId) {
        return shareRepository.findByPostId(postId);
    }
    
    // Follow operations
    public Follow followUser(UUID followerId, UUID followedId) {
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }
        
        if (followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new IllegalStateException("User is already following this user");
        }
        
        Follow follow = new Follow(followerId, followedId);
        return followRepository.save(follow);
    }
    
    public void unfollowUser(UUID followerId, UUID followedId) {
        followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
    }
    
    public boolean isUserFollowing(UUID followerId, UUID followedId) {
        return followRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }
    
    public long getFollowersCount(UUID userId) {
        return followRepository.countByFollowedId(userId);
    }
    
    public long getFollowingCount(UUID userId) {
        return followRepository.countByFollowerId(userId);
    }
    
    public List<Follow> getUserFollowers(UUID userId) {
        return followRepository.findByFollowedId(userId);
    }
    
    public List<Follow> getUserFollowing(UUID userId) {
        return followRepository.findByFollowerId(userId);
    }
}
