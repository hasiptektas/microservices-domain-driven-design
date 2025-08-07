// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.repository;

import com.example.interaction.domain.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {
    
    List<Follow> findByFollowerId(String followerId);
    
    List<Follow> findByFollowedId(String followedId);
    
    Optional<Follow> findByFollowerIdAndFollowedId(String followerId, String followedId);
    
    boolean existsByFollowerIdAndFollowedId(String followerId, String followedId);
    
    int countByFollowerId(String followerId);
    
    int countByFollowedId(String followedId);
    
    void deleteByFollowerIdAndFollowedId(String followerId, String followedId);
    
    @Query("SELECT f.followedId FROM Follow f WHERE f.followerId = :followerId")
    List<String> findFollowedIdsByFollowerId(@Param("followerId") String followerId);
    
    @Query("SELECT f.followerId FROM Follow f WHERE f.followedId = :followedId")
    List<String> findFollowerIdsByFollowedId(@Param("followedId") String followedId);
}
