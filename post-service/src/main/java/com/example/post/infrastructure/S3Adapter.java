// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.post.infrastructure;

/**
 * S3Adapter - Infrastructure Service
 * AWS S3 benzeri depolama sistemleri ile entegrasyon için adapter
 */
public class S3Adapter {
    
    private static final String BUCKET_NAME = "instagram-media-bucket";
    
    /**
     * Dosyayı S3'e yükle
     * @param fileName Dosya adı
     * @param fileContent Dosya içeriği (byte array)
     * @param contentType MIME type
     * @return S3 URL
     */
    public String uploadFile(String fileName, byte[] fileContent, String contentType) {
        // Gerçek implementasyonda AWS S3 SDK kullanılacak
        String s3Url = "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + fileName;
        
        System.out.println("Dosya S3'e yüklendi: " + fileName + " -> " + s3Url);
        
        // Gerçek implementasyon:
        // AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setContentType(contentType);
        // metadata.setContentLength(fileContent.length);
        // 
        // s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, 
        //     new ByteArrayInputStream(fileContent), metadata));
        
        return s3Url;
    }
    
    /**
     * S3'ten dosya sil
     * @param fileName Silinecek dosya adı
     * @return Başarı durumu
     */
    public boolean deleteFile(String fileName) {
        System.out.println("S3'ten dosya silindi: " + fileName);
        
        // Gerçek implementasyon:
        // s3Client.deleteObject(BUCKET_NAME, fileName);
        
        return true;
    }
    
    /**
     * Dosya URL'sinden dosya adını çıkar
     * @param s3Url S3 URL'si
     * @return Dosya adı
     */
    public String extractFileNameFromUrl(String s3Url) {
        if (s3Url != null && s3Url.contains("/")) {
            return s3Url.substring(s3Url.lastIndexOf("/") + 1);
        }
        return null;
    }
}
