package com.example.media.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class S3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    // Bu örnekte basit bir dosya sistemi implementasyonu
    // Gerçek uygulamada AWS S3 SDK kullanılır
    private final Path uploadPath = Paths.get("uploads");

    public S3Service() {
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String uploadFile(MultipartFile file, String s3Key) throws IOException {
        Path filePath = uploadPath.resolve(s3Key);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath);
        
        // Gerçek S3 URL'i simüle et
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Key;
    }

    public Resource downloadFile(String s3Key) {
        try {
            Path filePath = uploadPath.resolve(s3Key);
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteFile(String s3Key) {
        try {
            Path filePath = uploadPath.resolve(s3Key);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete the file", e);
        }
    }

    public URL generatePresignedUrl(String s3Key, long expirationInSeconds) {
        // Gerçek uygulamada AWS S3 SDK ile presigned URL oluşturulur
        // Bu örnekte basit bir URL döndürüyoruz
        try {
            return new URL("https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Key);
        } catch (Exception e) {
            throw new RuntimeException("Could not generate presigned URL", e);
        }
    }
} 