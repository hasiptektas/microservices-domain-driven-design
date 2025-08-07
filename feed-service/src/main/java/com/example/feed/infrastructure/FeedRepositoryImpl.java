// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.feed.infrastructure;

import com.example.feed.domain.model.FeedItem;
import com.example.feed.repository.FeedRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class FeedRepositoryImpl implements FeedRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String USER_FEED_KEY = "user:feed:";
    private static final String TRENDING_FEED_KEY = "feed:trending";
    private static final String EXPLORE_FEED_KEY = "feed:explore";
    private static final String POST_LIKES_KEY = "post:likes:";
    private static final String POST_COMMENTS_KEY = "post:comments:";
    private static final String POST_SHARES_KEY = "post:shares:";
    private static final String USER_FOLLOWING_KEY = "user:following:";

    @Override
    public List<FeedItem> getUserFeedFromCache(String userId) {
        try {
            String key = USER_FEED_KEY + userId;
            String cachedFeed = (String) redisTemplate.opsForValue().get(key);
            
            if (cachedFeed != null) {
                return objectMapper.readValue(cachedFeed, new TypeReference<List<FeedItem>>() {});
            }
        } catch (Exception e) {
            // Cache error durumunda boş liste döndür
        }
        return new ArrayList<>();
    }

    @Override
    public void cacheUserFeed(String userId, List<FeedItem> feed) {
        try {
            String key = USER_FEED_KEY + userId;
            String feedJson = objectMapper.writeValueAsString(feed);
            redisTemplate.opsForValue().set(key, feedJson, 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            // Cache error durumunda loglama yapılabilir
        }
    }

    @Override
    public void clearUserFeedCache(String userId) {
        String key = USER_FEED_KEY + userId;
        redisTemplate.delete(key);
    }

    @Override
    public List<FeedItem> getTrendingFeed() {
        try {
            String cachedFeed = (String) redisTemplate.opsForValue().get(TRENDING_FEED_KEY);
            if (cachedFeed != null) {
                return objectMapper.readValue(cachedFeed, new TypeReference<List<FeedItem>>() {});
            }
        } catch (Exception e) {
            // Cache error durumunda boş liste döndür
        }
        return new ArrayList<>();
    }

    @Override
    public List<FeedItem> getExploreFeed() {
        try {
            String cachedFeed = (String) redisTemplate.opsForValue().get(EXPLORE_FEED_KEY);
            if (cachedFeed != null) {
                return objectMapper.readValue(cachedFeed, new TypeReference<List<FeedItem>>() {});
            }
        } catch (Exception e) {
            // Cache error durumunda boş liste döndür
        }
        return new ArrayList<>();
    }

    @Override
    public List<FeedItem> getFeedByHashtag(String hashtag) {
        // Hashtag bazlı feed implementasyonu
        // Bu örnekte basit bir implementasyon
        return new ArrayList<>();
    }

    @Override
    public List<FeedItem> getFeedByUser(String targetUserId, String currentUserId) {
        // Kullanıcı profili feed'i
        // Bu örnekte basit bir implementasyon
        return new ArrayList<>();
    }

    @Override
    public void addPostToFeed(String postId, String userId) {
        // Yeni post'u takip edilen kullanıcıların feed'lerine ekle
        Set<String> followers = getFollowers(userId);
        for (String followerId : followers) {
            clearUserFeedCache(followerId);
        }
    }

    @Override
    public void removePostFromFeed(String postId, String userId) {
        // Post'u feed'lerden kaldır
        Set<String> followers = getFollowers(userId);
        for (String followerId : followers) {
            clearUserFeedCache(followerId);
        }
    }

    @Override
    public void updatePostInFeed(String postId, String userId) {
        // Post güncellemesi durumunda cache'i temizle
        Set<String> followers = getFollowers(userId);
        for (String followerId : followers) {
            clearUserFeedCache(followerId);
        }
    }

    @Override
    public void updateLikeCount(String postId, int likeCount) {
        String key = POST_LIKES_KEY + postId;
        redisTemplate.opsForValue().set(key, likeCount);
    }

    @Override
    public void updateCommentCount(String postId, int commentCount) {
        String key = POST_COMMENTS_KEY + postId;
        redisTemplate.opsForValue().set(key, commentCount);
    }

    @Override
    public void updateShareCount(String postId, int shareCount) {
        String key = POST_SHARES_KEY + postId;
        redisTemplate.opsForValue().set(key, shareCount);
    }

    @Override
    public void addUserToFollowing(String userId, String targetUserId) {
        String key = USER_FOLLOWING_KEY + userId;
        redisTemplate.opsForSet().add(key, targetUserId);
        clearUserFeedCache(userId);
    }

    @Override
    public void removeUserFromFollowing(String userId, String targetUserId) {
        String key = USER_FOLLOWING_KEY + userId;
        redisTemplate.opsForSet().remove(key, targetUserId);
        clearUserFeedCache(userId);
    }

    private Set<String> getFollowers(String userId) {
        // Bu metod gerçek implementasyonda user service'den follower listesini alır
        // Şimdilik boş set döndürüyoruz
        return Set.of();
    }
} 