package com.example.tagging.repository;

import com.example.tagging.domain.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    
    List<Tag> findByType(String type);
    
    List<Tag> findByPostId(String postId);
    
    List<Tag> findByUserId(String userId);
    
    @Query("SELECT t FROM Tag t WHERE t.type = 'HASHTAG' ORDER BY t.usageCount DESC")
    List<Tag> findTrendingHashtags();
    
    @Query("SELECT DISTINCT t.postId FROM Tag t WHERE t.name = :hashtag AND t.type = 'HASHTAG'")
    List<String> findPostIdsByHashtag(@Param("hashtag") String hashtag);
    
    @Query("SELECT DISTINCT t.postId FROM Tag t WHERE t.name = :username AND t.type = 'MENTION'")
    List<String> findPostIdsByMention(@Param("username") String username);
    
    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:query% ORDER BY t.usageCount DESC")
    List<Tag> searchTags(@Param("query") String query);
    
    void followHashtag(String userId, String hashtag);
    
    void unfollowHashtag(String userId, String hashtag);
    
    @Query("SELECT DISTINCT t.name FROM Tag t WHERE t.type = 'HASHTAG' AND t.userId = :userId")
    List<String> findFollowedHashtagsByUserId(@Param("userId") String userId);
} 