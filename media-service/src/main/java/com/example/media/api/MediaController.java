package com.example.media.api;

import com.example.media.application.MediaCommandHandler;
import com.example.media.domain.model.MediaFile;
import com.example.media.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media")
@CrossOrigin(origins = "*")
public class MediaController {

    @Autowired
    private MediaCommandHandler mediaCommandHandler;

    @Autowired
    private MediaRepository mediaRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedia(@RequestParam("file") MultipartFile file,
                                       @RequestParam("userId") String userId,
                                       @RequestParam(value = "postId", required = false) String postId) {
        try {
            MediaFile mediaFile = mediaCommandHandler.uploadMedia(file, userId, postId);
            return ResponseEntity.ok(new UploadResponse(mediaFile.getMediaId(), mediaFile.getS3Url()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{mediaId}")
    public ResponseEntity<MediaFile> getMediaInfo(@PathVariable String mediaId) {
        Optional<MediaFile> mediaFile = mediaRepository.findById(mediaId);
        return mediaFile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{mediaId}/download")
    public ResponseEntity<Resource> downloadMedia(@PathVariable String mediaId) {
        try {
            Resource resource = mediaCommandHandler.downloadMedia(mediaId);
            MediaFile mediaFile = mediaRepository.findById(mediaId).orElseThrow();
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mediaFile.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=\"" + mediaFile.getOriginalFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<?> deleteMedia(@PathVariable String mediaId) {
        try {
            mediaCommandHandler.deleteMedia(mediaId);
            return ResponseEntity.ok(new SuccessResponse("Medya dosyası silindi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MediaFile>> getUserMedia(@PathVariable String userId) {
        List<MediaFile> mediaFiles = mediaRepository.findByUserId(userId);
        return ResponseEntity.ok(mediaFiles);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<MediaFile>> getPostMedia(@PathVariable String postId) {
        List<MediaFile> mediaFiles = mediaRepository.findByPostId(postId);
        return ResponseEntity.ok(mediaFiles);
    }

    @PutMapping("/{mediaId}/process")
    public ResponseEntity<?> processMedia(@PathVariable String mediaId) {
        try {
            mediaCommandHandler.processMedia(mediaId);
            return ResponseEntity.ok(new SuccessResponse("Medya işleme başlatıldı"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{mediaId}/status")
    public ResponseEntity<String> getMediaStatus(@PathVariable String mediaId) {
        Optional<MediaFile> mediaFile = mediaRepository.findById(mediaId);
        return mediaFile.map(file -> ResponseEntity.ok(file.getStatus().name()))
                .orElse(ResponseEntity.notFound().build());
    }

    // Request/Response DTOs
    public static class UploadResponse {
        private String mediaId;
        private String url;

        public UploadResponse(String mediaId, String url) {
            this.mediaId = mediaId;
            this.url = url;
        }

        public String getMediaId() { return mediaId; }
        public String getUrl() { return url; }
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