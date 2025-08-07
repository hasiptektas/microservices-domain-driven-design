// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.media.repository;

import com.example.media.domain.model.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<MediaFile, String> {
    
    List<MediaFile> findByUserId(String userId);
    
    List<MediaFile> findByPostId(String postId);
    
    List<MediaFile> findByUserIdAndStatus(String userId, MediaFile.MediaStatus status);
    
    List<MediaFile> findByPostIdAndStatus(String postId, MediaFile.MediaStatus status);
    
    void deleteByUserId(String userId);
    
    void deleteByPostId(String postId);
} 