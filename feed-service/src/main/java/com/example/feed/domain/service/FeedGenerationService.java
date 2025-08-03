package com.example.feed.domain.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * FeedGenerationService - Domain Service  
 * Kullanıcının takip ettiği kişilerin gönderilerini getirerek ana sayfa akışını oluşturur
 */
public class FeedGenerationService {
    
    /**
     * Kullanıcının ana sayfa akışını oluşturur
     * @param userId Kullanıcı ID'si
     * @param followingUserIds Takip edilen kullanıcı ID'leri
     * @param page Sayfa numarası
     * @param size Sayfa boyutu
     * @return Feed item'ları listesi
     */
    public List<FeedItem> generateUserFeed(String userId, List<String> followingUserIds, int page, int size) {
        // Bu metod, post-service'den takip edilen kullanıcıların gönderilerini getirir
        // Gerçek implementasyonda REST client veya message queue kullanılır
        
        System.out.println("Kullanıcı " + userId + " için feed oluşturuluyor...");
        System.out.println("Takip edilen kullanıcılar: " + followingUserIds);
        
        // Örnek implementasyon - gerçekte external service call yapılacak
        return List.of(
            new FeedItem("post1", "user2", "Güzel bir günde...", LocalDateTime.now().minusHours(1)),
            new FeedItem("post2", "user3", "Yemek fotoğrafı", LocalDateTime.now().minusHours(2))
        );
    }
    
    /**
     * Trending/popüler gönderileri getirir
     * @param page Sayfa numarası  
     * @param size Sayfa boyutu
     * @return Trending feed item'ları
     */
    public List<FeedItem> generateTrendingFeed(int page, int size) {
        // Interaction-service'den beğeni/yorum sayısı yüksek gönderileri getirir
        System.out.println("Trending feed oluşturuluyor...");
        
        return List.of(
            new FeedItem("trending1", "popular_user", "Viral gönderi", LocalDateTime.now().minusHours(3)),
            new FeedItem("trending2", "influencer", "Popüler içerik", LocalDateTime.now().minusHours(4))
        );
    }
    
    /**
     * Kullanıcı için önerilen gönderileri oluşturur
     * @param userId Kullanıcı ID'si
     * @param userInterests Kullanıcının ilgi alanları
     * @return Önerilen feed item'ları
     */
    public List<FeedItem> generateRecommendedFeed(String userId, List<String> userInterests) {
        // AI/ML algoritması ile kişiselleştirilmiş öneriler
        System.out.println("Kullanıcı " + userId + " için önerilen feed oluşturuluyor...");
        System.out.println("İlgi alanları: " + userInterests);
        
        return List.of(
            new FeedItem("rec1", "similar_user", "İlgini çekebilir", LocalDateTime.now().minusHours(5))
        );
    }
}

/**
 * FeedItem - Feed'de gösterilecek gönderi bilgileri
 */
class FeedItem {
    private final String postId;
    private final String userId;
    private final String caption;
    private final LocalDateTime createdAt;
    
    public FeedItem(String postId, String userId, String caption, LocalDateTime createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.caption = caption;
        this.createdAt = createdAt;
    }
    
    // Getters
    public String getPostId() { return postId; }
    public String getUserId() { return userId; }
    public String getCaption() { return caption; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
