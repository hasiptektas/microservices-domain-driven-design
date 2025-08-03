package com.example.post.api;

import com.example.post.application.PostCommandHandler;
import com.example.post.domain.model.MediaFile;
import com.example.post.domain.model.Post;
import com.example.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;

/**
 * PostController - API Layer
 * Post servisi için REST API endpoint'lerini sağlar
 */
public class PostController {
    
    private final PostCommandHandler postCommandHandler;
    private final PostRepository postRepository;
    
    public PostController(PostCommandHandler postCommandHandler, PostRepository postRepository) {
        this.postCommandHandler = postCommandHandler;
        this.postRepository = postRepository;
    }
    
    /**
     * Yeni gönderi oluştur
     * POST /api/posts
     */
    public PostResponse createPost(CreatePostRequest request) {
        try {
            MediaFile mediaFile = new MediaFile(
                request.getMediaUrl(),
                request.getFileName(),
                request.getFileType(),
                request.getFileSize(),
                request.getWidth(),
                request.getHeight()
            );
            
            String postId = postCommandHandler.createPost(
                request.getUserId(),
                request.getCaption(),
                mediaFile
            );
            
            return new PostResponse(true, "Gönderi başarıyla oluşturuldu", postId);
        } catch (Exception e) {
            return new PostResponse(false, "Gönderi oluşturulamadı: " + e.getMessage(), null);
        }
    }
    
    /**
     * Gönderi detayını getir
     * GET /api/posts/{postId}
     */
    public PostDetailResponse getPost(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        
        if (post.isPresent()) {
            return PostDetailResponse.fromPost(post.get());
        } else {
            return new PostDetailResponse(false, "Gönderi bulunamadı", null);
        }
    }
    
    /**
     * Kullanıcının gönderilerini listele
     * GET /api/posts/user/{userId}
     */
    public PostListResponse getUserPosts(String userId, int page, int size) {
        try {
            List<Post> posts = postRepository.findByUserId(userId);
            
            // Sayfalama
            int start = page * size;
            int end = Math.min(start + size, posts.size());
            List<Post> pagedPosts = posts.subList(start, end);
            
            return PostListResponse.fromPosts(pagedPosts, true, "Başarılı");
        } catch (Exception e) {
            return new PostListResponse(false, "Gönderiler getirilemedi: " + e.getMessage(), null);
        }
    }
    
    /**
     * Gönderi caption'ını güncelle
     * PUT /api/posts/{postId}/caption
     */
    public PostResponse updatePostCaption(String postId, UpdateCaptionRequest request) {
        try {
            boolean success = postCommandHandler.updatePostCaption(
                postId,
                request.getNewCaption(),
                request.getUserId()
            );
            
            if (success) {
                return new PostResponse(true, "Caption başarıyla güncellendi", postId);
            } else {
                return new PostResponse(false, "Gönderi bulunamadı veya yetkiniz yok", null);
            }
        } catch (Exception e) {
            return new PostResponse(false, "Caption güncellenemedi: " + e.getMessage(), null);
        }
    }
    
    /**
     * Gönderi sil
     * DELETE /api/posts/{postId}
     */
    public PostResponse deletePost(String postId, String userId) {
        try {
            boolean success = postCommandHandler.deletePost(postId, userId);
            
            if (success) {
                return new PostResponse(true, "Gönderi başarıyla silindi", postId);
            } else {
                return new PostResponse(false, "Gönderi bulunamadı veya yetkiniz yok", null);
            }
        } catch (Exception e) {
            return new PostResponse(false, "Gönderi silinemedi: " + e.getMessage(), null);
        }
    }
    
    /**
     * Gönderiye yorum ekle
     * POST /api/posts/{postId}/comments
     */
    public PostResponse addComment(String postId, AddCommentRequest request) {
        try {
            boolean success = postCommandHandler.addComment(
                postId,
                request.getUserId(),
                request.getContent()
            );
            
            if (success) {
                return new PostResponse(true, "Yorum başarıyla eklendi", postId);
            } else {
                return new PostResponse(false, "Gönderi bulunamadı", null);
            }
        } catch (Exception e) {
            return new PostResponse(false, "Yorum eklenemedi: " + e.getMessage(), null);
        }
    }
    
    /**
     * Arama yap
     * GET /api/posts/search?keyword={keyword}
     */
    public PostListResponse searchPosts(String keyword) {
        try {
            List<Post> posts = postRepository.findByCaptionContaining(keyword);
            return PostListResponse.fromPosts(posts, true, "Arama başarılı");
        } catch (Exception e) {
            return new PostListResponse(false, "Arama yapılamadı: " + e.getMessage(), null);
        }
    }
}

// DTO Classes
class CreatePostRequest {
    private String userId;
    private String caption;
    private String mediaUrl;
    private String fileName;
    private String fileType;
    private long fileSize;
    private int width;
    private int height;
    
    // Getters
    public String getUserId() { return userId; }
    public String getCaption() { return caption; }
    public String getMediaUrl() { return mediaUrl; }
    public String getFileName() { return fileName; }
    public String getFileType() { return fileType; }
    public long getFileSize() { return fileSize; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

class UpdateCaptionRequest {
    private String userId;
    private String newCaption;
    
    public String getUserId() { return userId; }
    public String getNewCaption() { return newCaption; }
}

class AddCommentRequest {
    private String userId;
    private String content;
    
    public String getUserId() { return userId; }
    public String getContent() { return content; }
}

class PostResponse {
    private boolean success;
    private String message;
    private String postId;
    
    public PostResponse(boolean success, String message, String postId) {
        this.success = success;
        this.message = message;
        this.postId = postId;
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getPostId() { return postId; }
}

class PostDetailResponse {
    private boolean success;
    private String message;
    private Post post;
    
    public PostDetailResponse(boolean success, String message, Post post) {
        this.success = success;
        this.message = message;
        this.post = post;
    }
    
    public static PostDetailResponse fromPost(Post post) {
        return new PostDetailResponse(true, "Başarılı", post);
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Post getPost() { return post; }
}

class PostListResponse {
    private boolean success;
    private String message;
    private List<Post> posts;
    
    public PostListResponse(boolean success, String message, List<Post> posts) {
        this.success = success;
        this.message = message;
        this.posts = posts;
    }
    
    public static PostListResponse fromPosts(List<Post> posts, boolean success, String message) {
        return new PostListResponse(success, message, posts);
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Post> getPosts() { return posts; }
}
