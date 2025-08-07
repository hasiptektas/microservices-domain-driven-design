// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.interaction.api;

import com.example.interaction.application.InteractionCommandHandler;
import com.example.interaction.domain.model.Like;
import com.example.interaction.domain.model.Share;
import com.example.interaction.repository.LikeRepository;
import com.example.interaction.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/interactions")
@CrossOrigin(origins = "*")
public class InteractionController {

    @Autowired
    private InteractionCommandHandler interactionCommandHandler;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ShareRepository shareRepository;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable String postId, @RequestBody LikeRequest request) {
        try {
            interactionCommandHandler.likePost(request.getUserId(), postId);
            return ResponseEntity.ok(new SuccessResponse("Post beğenildi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/posts/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable String postId, @RequestBody UnlikeRequest request) {
        try {
            interactionCommandHandler.unlikePost(request.getUserId(), postId);
            return ResponseEntity.ok(new SuccessResponse("Post beğenisi kaldırıldı"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/posts/{postId}/share")
    public ResponseEntity<?> sharePost(@PathVariable String postId, @RequestBody ShareRequest request) {
        try {
            interactionCommandHandler.sharePost(request.getUserId(), postId, request.getPlatform());
            return ResponseEntity.ok(new SuccessResponse("Post paylaşıldı"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/posts/{postId}/likes")
    public ResponseEntity<List<Like>> getPostLikes(@PathVariable String postId) {
        List<Like> likes = likeRepository.findByPostId(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/posts/{postId}/shares")
    public ResponseEntity<List<Share>> getPostShares(@PathVariable String postId) {
        List<Share> shares = shareRepository.findByPostId(postId);
        return ResponseEntity.ok(shares);
    }

    @GetMapping("/posts/{postId}/like-count")
    public ResponseEntity<Integer> getPostLikeCount(@PathVariable String postId) {
        int likeCount = likeRepository.countByPostId(postId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/posts/{postId}/share-count")
    public ResponseEntity<Integer> getPostShareCount(@PathVariable String postId) {
        int shareCount = shareRepository.countByPostId(postId);
        return ResponseEntity.ok(shareCount);
    }

    @GetMapping("/users/{userId}/liked-posts")
    public ResponseEntity<List<String>> getUserLikedPosts(@PathVariable String userId) {
        List<Like> likes = likeRepository.findByUserId(userId);
        List<String> postIds = likes.stream()
                .map(Like::getPostId)
                .toList();
        return ResponseEntity.ok(postIds);
    }

    @GetMapping("/users/{userId}/shared-posts")
    public ResponseEntity<List<String>> getUserSharedPosts(@PathVariable String userId) {
        List<Share> shares = shareRepository.findByUserId(userId);
        List<String> postIds = shares.stream()
                .map(Share::getPostId)
                .toList();
        return ResponseEntity.ok(postIds);
    }

    @GetMapping("/posts/{postId}/users/{userId}/is-liked")
    public ResponseEntity<Boolean> isPostLikedByUser(@PathVariable String postId, @PathVariable String userId) {
        Optional<Like> like = likeRepository.findByUserIdAndPostId(userId, postId);
        return ResponseEntity.ok(like.isPresent());
    }

    // Request/Response DTOs
    public static class LikeRequest {
        private String userId;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }

    public static class UnlikeRequest {
        private String userId;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }

    public static class ShareRequest {
        private String userId;
        private String platform;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getPlatform() { return platform; }
        public void setPlatform(String platform) { this.platform = platform; }
    }

    public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
    }

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { return error; }
    }
} 