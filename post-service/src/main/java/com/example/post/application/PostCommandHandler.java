package com.example.post.application;

import com.example.post.domain.model.Post;
import com.example.post.domain.model.MediaFile;
import com.example.post.domain.service.TaggingService;
import com.example.post.domain.event.UserMentionedEvent;
import com.example.post.repository.PostRepository;
import com.example.post.infrastructure.KafkaPublisher;

/**
 * PostCommandHandler - Application Service
 * Post ile ilgili komutları işleyen uygulama servisi
 */
public class PostCommandHandler {
    private final PostRepository postRepository;
    private final TaggingService taggingService;
    private final KafkaPublisher kafkaPublisher;

    public PostCommandHandler(PostRepository postRepository, TaggingService taggingService, KafkaPublisher kafkaPublisher) {
        this.postRepository = postRepository;
        this.taggingService = taggingService;
        this.kafkaPublisher = kafkaPublisher;
    }

    /**
     * Yeni gönderi oluştur
     * @param userId Kullanıcı ID'si
     * @param caption Gönderi açıklaması
     * @param mediaFile Medya dosyası
     * @return Oluşturulan gönderi ID'si
     */
    public String createPost(String userId, String caption, MediaFile mediaFile) {
        // Domain object oluştur
        Post post = new Post(userId, caption, mediaFile);
        
        // Repository'ye kaydet
        Post savedPost = postRepository.save(post);
        
        // Domain events'leri yayınla
        publishDomainEvents(savedPost);
        
        // Etiketleme kontrolü yap
        UserMentionedEvent mentionEvent = taggingService.createMentionEventIfExists(
            savedPost.getPostId(), userId, caption);
        
        if (mentionEvent != null) {
            kafkaPublisher.publishUserMentionedEvent(mentionEvent);
        }
        
        return savedPost.getPostId();
    }

    /**
     * Gönderi caption'ını güncelle
     * @param postId Gönderi ID'si
     * @param newCaption Yeni açıklama
     * @param userId İşlemi yapan kullanıcı ID'si
     * @return Başarı durumu
     */
    public boolean updatePostCaption(String postId, String newCaption, String userId) {
        return postRepository.findById(postId)
            .map(post -> {
                // Yetki kontrolü
                if (!post.getUserId().equals(userId)) {
                    throw new IllegalArgumentException("Bu gönderiye düzenleme yetkiniz yok");
                }
                
                post.updateCaption(newCaption);
                postRepository.save(post);
                
                // Yeni etiketlemeler varsa event yayınla
                UserMentionedEvent mentionEvent = taggingService.createMentionEventIfExists(
                    postId, userId, newCaption);
                
                if (mentionEvent != null) {
                    kafkaPublisher.publishUserMentionedEvent(mentionEvent);
                }
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Gönderi sil
     * @param postId Gönderi ID'si
     * @param userId İşlemi yapan kullanıcı ID'si
     * @return Başarı durumu
     */
    public boolean deletePost(String postId, String userId) {
        return postRepository.findById(postId)
            .map(post -> {
                // Yetki kontrolü
                if (!post.getUserId().equals(userId)) {
                    throw new IllegalArgumentException("Bu gönderiye silme yetkiniz yok");
                }
                
                postRepository.deleteById(postId);
                return true;
            })
            .orElse(false);
    }

    /**
     * Gönderiye yorum ekle
     * @param postId Gönderi ID'si
     * @param userId Yorum yapan kullanıcı ID'si
     * @param content Yorum içeriği
     * @return Başarı durumu
     */
    public boolean addComment(String postId, String userId, String content) {
        return postRepository.findById(postId)
            .map(post -> {
                post.addComment(userId, content);
                postRepository.save(post);
                return true;
            })
            .orElse(false);
    }

    private void publishDomainEvents(Post post) {
        // Post oluşturulduğunda PostCreatedEvent'i yayınla
        post.getDomainEvents().forEach(event -> {
            kafkaPublisher.publishPostCreatedEvent(event);
        });
    }
}
