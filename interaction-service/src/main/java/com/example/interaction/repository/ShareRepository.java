package com.example.interaction.repository;

import com.example.interaction.domain.model.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, String> {
    
    List<Share> findByPostId(String postId);
    
    List<Share> findByUserId(String userId);
    
    int countByPostId(String postId);
    
    int countByUserId(String userId);
    
    void deleteByPostId(String postId);
    
    @Query("SELECT s.postId FROM Share s WHERE s.userId = :userId")
    List<String> findPostIdsByUserId(@Param("userId") String userId);
}
