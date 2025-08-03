package com.example.post.domain.model;

/**
 * MediaFile Value Object
 * Medya dosyası bilgilerini kapsayan değer nesnesi
 */
public class MediaFile {
    private final String url;
    private final String fileName;
    private final String fileType;
    private final long fileSize;
    private final int width;
    private final int height;

    public MediaFile(String url, String fileName, String fileType, long fileSize, int width, int height) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL boş olamaz");
        }
        if (fileSize <= 0) {
            throw new IllegalArgumentException("Dosya boyutu pozitif olmalıdır");
        }
        
        this.url = url;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.width = width;
        this.height = height;
    }

    public boolean isImage() {
        return fileType.startsWith("image/");
    }

    public boolean isVideo() {
        return fileType.startsWith("video/");
    }

    // Getters
    public String getUrl() { return url; }
    public String getFileName() { return fileName; }
    public String getFileType() { return fileType; }
    public long getFileSize() { return fileSize; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MediaFile mediaFile = (MediaFile) obj;
        return fileSize == mediaFile.fileSize &&
               width == mediaFile.width &&
               height == mediaFile.height &&
               url.equals(mediaFile.url) &&
               fileName.equals(mediaFile.fileName) &&
               fileType.equals(mediaFile.fileType);
    }

    @Override
    public int hashCode() {
        return url.hashCode() + fileName.hashCode() + fileType.hashCode();
    }
}
