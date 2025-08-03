package com.example.interaction.repository;

import com.example.interaction.domain.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    
    List<Like> findByPostId(String postId);
    
    List<Like> findByUserId(String userId);
    
    Optional<Like> findByUserIdAndPostId(String userId, String postId);
    
    int countByPostId(String postId);
    
    int countByUserId(String userId);
    
    boolean existsByUserIdAndPostId(String userId, String postId);
    
    void deleteByUserIdAndPostId(String userId, String postId);
    
    void deleteByPostId(String postId);
    
    @Query("SELECT l.postId FROM Like l WHERE l.userId = :userId")
    List<String> findPostIdsByUserId(@Param("userId") String userId);
}
