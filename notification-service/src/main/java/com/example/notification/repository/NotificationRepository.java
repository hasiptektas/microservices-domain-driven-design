package com.example.notification.repository;

import com.example.notification.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    
    List<Notification> findByUserIdOrderByCreatedAtDesc(String userId);
    
    List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(String userId);
    
    int countByUserIdAndReadFalse(String userId);
    
    List<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(String userId, String type);
    
    @Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.type = :type AND n.read = false")
    List<Notification> findUnreadByUserIdAndType(@Param("userId") String userId, @Param("type") String type);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.type = :type AND n.read = false")
    int countUnreadByUserIdAndType(@Param("userId") String userId, @Param("type") String type);
    
    void deleteByUserId(String userId);
    
    void deleteByUserIdAndType(String userId, String type);
} 