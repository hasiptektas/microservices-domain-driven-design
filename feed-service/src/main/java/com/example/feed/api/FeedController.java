// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.feed.api;

import com.example.feed.application.FeedQueryHandler;
import com.example.feed.domain.model.FeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin(origins = "*")
public class FeedController {

    @Autowired
    private FeedQueryHandler feedQueryHandler;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FeedItem>> getUserFeed(@PathVariable String userId) {
        try {
            List<FeedItem> feed = feedQueryHandler.getUserFeed(userId);
            return ResponseEntity.ok(feed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/trending")
    public ResponseEntity<List<FeedItem>> getTrendingFeed() {
        try {
            List<FeedItem> trendingFeed = feedQueryHandler.getTrendingFeed();
            return ResponseEntity.ok(trendingFeed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/recommended/{userId}")
    public ResponseEntity<List<FeedItem>> getRecommendedFeed(@PathVariable String userId) {
        try {
            List<FeedItem> recommendedFeed = feedQueryHandler.getRecommendedFeed(userId);
            return ResponseEntity.ok(recommendedFeed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/explore")
    public ResponseEntity<List<FeedItem>> getExploreFeed() {
        try {
            List<FeedItem> exploreFeed = feedQueryHandler.getExploreFeed();
            return ResponseEntity.ok(exploreFeed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refresh/{userId}")
    public ResponseEntity<String> refreshUserFeed(@PathVariable String userId) {
        try {
            feedQueryHandler.refreshUserFeed(userId);
            return ResponseEntity.ok("Feed başarıyla yenilendi");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Feed yenilenirken hata oluştu: " + e.getMessage());
        }
    }
} 