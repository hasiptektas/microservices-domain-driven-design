// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.media.application;

import com.example.media.domain.model.MediaFile;
import com.example.media.repository.MediaRepository;
import com.example.media.infrastructure.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class MediaCommandHandler {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private S3Service s3Service;

    public MediaFile uploadMedia(MultipartFile file, String userId, String postId) throws IOException {
        // Dosya validasyonu
        validateFile(file);

        // Dosya adı oluştur
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // MediaFile entity oluştur
        MediaFile mediaFile = new MediaFile(
            fileName,
            originalFileName,
            fileExtension,
            file.getContentType(),
            file.getSize(),
            userId,
            postId
        );

        // S3'e yükle
        String s3Key = "uploads/" + userId + "/" + fileName;
        String s3Url = s3Service.uploadFile(file, s3Key);
        
        mediaFile.setS3Key(s3Key);
        mediaFile.setS3Url(s3Url);
        mediaFile.markAsCompleted();

        // Veritabanına kaydet
        return mediaRepository.save(mediaFile);
    }

    public Resource downloadMedia(String mediaId) {
        MediaFile mediaFile = mediaRepository.findById(mediaId)
            .orElseThrow(() -> new RuntimeException("Media file not found"));

        return s3Service.downloadFile(mediaFile.getS3Key());
    }

    public void deleteMedia(String mediaId) {
        MediaFile mediaFile = mediaRepository.findById(mediaId)
            .orElseThrow(() -> new RuntimeException("Media file not found"));

        // S3'den sil
        s3Service.deleteFile(mediaFile.getS3Key());

        // Veritabanından sil
        mediaRepository.deleteById(mediaId);
    }

    public void processMedia(String mediaId) {
        MediaFile mediaFile = mediaRepository.findById(mediaId)
            .orElseThrow(() -> new RuntimeException("Media file not found"));

        mediaFile.markAsProcessing();
        mediaRepository.save(mediaFile);

        // Asenkron işleme başlat
        // Bu örnekte basit bir implementasyon
        // Gerçek uygulamada background job queue kullanılır
        try {
            Thread.sleep(2000); // Simüle edilmiş işleme süresi
            mediaFile.markAsCompleted();
            mediaRepository.save(mediaFile);
        } catch (InterruptedException e) {
            mediaFile.markAsFailed();
            mediaRepository.save(mediaFile);
            Thread.currentThread().interrupt();
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (file.getSize() > 100 * 1024 * 1024) { // 100MB limit
            throw new RuntimeException("File size exceeds limit");
        }

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.startsWith("video/"))) {
            throw new RuntimeException("Invalid file type");
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
} 