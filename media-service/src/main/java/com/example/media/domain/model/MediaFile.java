// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.media.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class MediaFile {
    private String mediaId;
    private String fileName;
    private String originalFileName;
    private String fileType;
    private String mimeType;
    private long fileSize;
    private String s3Key;
    private String s3Url;
    private String userId;
    private String postId;
    private MediaStatus status;
    private LocalDateTime uploadedAt;
    private LocalDateTime processedAt;

    public enum MediaStatus {
        UPLOADING,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    public MediaFile(String fileName, String originalFileName, String fileType, 
                    String mimeType, long fileSize, String userId, String postId) {
        this.mediaId = UUID.randomUUID().toString();
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.postId = postId;
        this.status = MediaStatus.UPLOADING;
        this.uploadedAt = LocalDateTime.now();
    }

    // Getters
    public String getMediaId() { return mediaId; }
    public String getFileName() { return fileName; }
    public String getOriginalFileName() { return originalFileName; }
    public String getFileType() { return fileType; }
    public String getMimeType() { return mimeType; }
    public long getFileSize() { return fileSize; }
    public String getS3Key() { return s3Key; }
    public String getS3Url() { return s3Url; }
    public String getUserId() { return userId; }
    public String getPostId() { return postId; }
    public MediaStatus getStatus() { return status; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public LocalDateTime getProcessedAt() { return processedAt; }

    // Setters
    public void setMediaId(String mediaId) { this.mediaId = mediaId; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public void setS3Key(String s3Key) { this.s3Key = s3Key; }
    public void setS3Url(String s3Url) { this.s3Url = s3Url; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setPostId(String postId) { this.postId = postId; }
    public void setStatus(MediaStatus status) { this.status = status; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }

    public void markAsProcessing() {
        this.status = MediaStatus.PROCESSING;
    }

    public void markAsCompleted() {
        this.status = MediaStatus.COMPLETED;
        this.processedAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        this.status = MediaStatus.FAILED;
        this.processedAt = LocalDateTime.now();
    }

    public boolean isImage() {
        return mimeType != null && mimeType.startsWith("image/");
    }

    public boolean isVideo() {
        return mimeType != null && mimeType.startsWith("video/");
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "mediaId='" + mediaId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", fileSize=" + fileSize +
                ", s3Key='" + s3Key + '\'' +
                ", s3Url='" + s3Url + '\'' +
                ", userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", status=" + status +
                ", uploadedAt=" + uploadedAt +
                ", processedAt=" + processedAt +
                '}';
    }
} 