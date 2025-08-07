// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.post.infrastructure;

import com.example.post.domain.model.Post;
import com.example.post.domain.model.MediaFile;
import com.example.post.domain.model.Comment;
import com.example.post.repository.PostRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * PostRepositoryImpl - Infrastructure Implementation
 * PostRepository interface'inin in-memory implementasyonu
 * Gerçek uygulamada JPA veya MongoDB kullanılacak
 */
public class PostRepositoryImpl implements PostRepository {
    
    private final Map<String, Post> posts = new ConcurrentHashMap<>();
    
    @Override
    public Post save(Post post) {
        posts.put(post.getPostId(), post);
        return post;
    }
    
    @Override
    public Optional<Post> findById(String postId) {
        return Optional.ofNullable(posts.get(postId));
    }
    
    @Override
    public List<Post> findByUserId(String userId) {
        return posts.values().stream()
                .filter(post -> post.getUserId().equals(userId))
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Post> findAll(int page, int size) {
        return posts.values().stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(String postId) {
        posts.remove(postId);
    }
    
    @Override
    public List<Post> findByUserIdAndDateRange(String userId, String startDate, String endDate) {
        // Basit implementasyon - gerçek uygulamada tarih parse edilecek
        return findByUserId(userId);
    }
    
    @Override
    public List<Post> findByCaptionContaining(String keyword) {
        return posts.values().stream()
                .filter(post -> post.getCaption().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
