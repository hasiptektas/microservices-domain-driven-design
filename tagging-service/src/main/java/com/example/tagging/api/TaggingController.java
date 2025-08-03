package com.example.tagging.api;

import com.example.tagging.application.TaggingCommandHandler;
import com.example.tagging.domain.model.Tag;
import com.example.tagging.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tagging")
@CrossOrigin(origins = "*")
public class TaggingController {

    @Autowired
    private TaggingCommandHandler taggingCommandHandler;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/extract")
    public ResponseEntity<?> extractTags(@RequestBody ExtractTagsRequest request) {
        try {
            List<Tag> tags = taggingCommandHandler.extractTags(request.getContent(), request.getPostId(), request.getUserId());
            return ResponseEntity.ok(new ExtractTagsResponse(tags));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/hashtags/trending")
    public ResponseEntity<List<Tag>> getTrendingHashtags() {
        List<Tag> trendingHashtags = tagRepository.findTrendingHashtags();
        return ResponseEntity.ok(trendingHashtags);
    }

    @GetMapping("/hashtags/{hashtag}/posts")
    public ResponseEntity<List<String>> getPostsByHashtag(@PathVariable String hashtag) {
        List<String> postIds = tagRepository.findPostIdsByHashtag(hashtag);
        return ResponseEntity.ok(postIds);
    }

    @GetMapping("/mentions/{username}/posts")
    public ResponseEntity<List<String>> getPostsByMention(@PathVariable String username) {
        List<String> postIds = tagRepository.findPostIdsByMention(username);
        return ResponseEntity.ok(postIds);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Tag>> searchTags(@RequestParam String query) {
        List<Tag> tags = tagRepository.searchTags(query);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getTagSuggestions(@RequestParam String query) {
        List<String> suggestions = taggingCommandHandler.getTagSuggestions(query);
        return ResponseEntity.ok(suggestions);
    }

    @PostMapping("/hashtags/{hashtag}/follow")
    public ResponseEntity<?> followHashtag(@PathVariable String hashtag, @RequestBody FollowHashtagRequest request) {
        try {
            taggingCommandHandler.followHashtag(request.getUserId(), hashtag);
            return ResponseEntity.ok(new SuccessResponse("Hashtag takip edildi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/hashtags/{hashtag}/unfollow")
    public ResponseEntity<?> unfollowHashtag(@PathVariable String hashtag, @RequestBody UnfollowHashtagRequest request) {
        try {
            taggingCommandHandler.unfollowHashtag(request.getUserId(), hashtag);
            return ResponseEntity.ok(new SuccessResponse("Hashtag takipten çıkarıldı"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/users/{userId}/followed-hashtags")
    public ResponseEntity<List<String>> getFollowedHashtags(@PathVariable String userId) {
        List<String> followedHashtags = tagRepository.findFollowedHashtagsByUserId(userId);
        return ResponseEntity.ok(followedHashtags);
    }

    // Request/Response DTOs
    public static class ExtractTagsRequest {
        private String content;
        private String postId;
        private String userId;

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getPostId() { return postId; }
        public void setPostId(String postId) { this.postId = postId; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }

    public static class ExtractTagsResponse {
        private List<Tag> tags;

        public ExtractTagsResponse(List<Tag> tags) {
            this.tags = tags;
        }

        public List<Tag> getTags() { return tags; }
    }

    public static class FollowHashtagRequest {
        private String userId;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }

    public static class UnfollowHashtagRequest {
        private String userId;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
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