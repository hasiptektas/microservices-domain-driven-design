// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.feed.application;

import com.example.feed.domain.model.FeedItem;
import com.example.feed.domain.service.FeedGenerationService;
import com.example.feed.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedQueryHandler {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedGenerationService feedGenerationService;

    public List<FeedItem> getUserFeed(String userId) {
        // Önce cache'den kontrol et
        List<FeedItem> cachedFeed = feedRepository.getUserFeedFromCache(userId);
        if (cachedFeed != null && !cachedFeed.isEmpty()) {
            return cachedFeed;
        }

        // Cache'de yoksa generate et ve cache'e kaydet
        List<FeedItem> generatedFeed = feedGenerationService.generateUserFeed(userId);
        feedRepository.cacheUserFeed(userId, generatedFeed);
        
        return generatedFeed;
    }

    public List<FeedItem> getTrendingFeed() {
        return feedRepository.getTrendingFeed();
    }

    public List<FeedItem> getRecommendedFeed(String userId) {
        return feedGenerationService.generateRecommendedFeed(userId);
    }

    public List<FeedItem> getExploreFeed() {
        return feedRepository.getExploreFeed();
    }

    public void refreshUserFeed(String userId) {
        // Cache'i temizle
        feedRepository.clearUserFeedCache(userId);
        
        // Yeni feed generate et
        List<FeedItem> newFeed = feedGenerationService.generateUserFeed(userId);
        feedRepository.cacheUserFeed(userId, newFeed);
    }

    public List<FeedItem> getFeedByHashtag(String hashtag) {
        return feedRepository.getFeedByHashtag(hashtag);
    }

    public List<FeedItem> getFeedByUser(String targetUserId, String currentUserId) {
        return feedRepository.getFeedByUser(targetUserId, currentUserId);
    }
} 