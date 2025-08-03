package com.example.notification.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Notification Entity - Aggregate Root
 * Kullanıcıya gönderilen sistem bildirimlerini temsil eder
 */
public class Notification {
    private final String notificationId;
    private final String userId;
    private String message;
    private final NotificationType type;
    private final String sourceId; // Post ID, User ID, etc.
    private final LocalDateTime createdAt;
    private boolean seen;
    private LocalDateTime seenAt;

    public Notification(String userId, String message, NotificationType type, String sourceId) {
        this.notificationId = UUID.randomUUID().toString();
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.sourceId = sourceId;
        this.createdAt = LocalDateTime.now();
        this.seen = false;
    }

    // Bildirimi okundu olarak işaretle
    public void markAsSeen() {
        this.seen = true;
        this.seenAt = LocalDateTime.now();
    }

    // Bildirim mesajını güncelle
    public void updateMessage(String newMessage) {
        this.message = newMessage;
    }

    // Getters
    public String getNotificationId() { return notificationId; }
    public String getUserId() { return userId; }
    public String getMessage() { return message; }
    public NotificationType getType() { return type; }
    public String getSourceId() { return sourceId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isSeen() { return seen; }
    public LocalDateTime getSeenAt() { return seenAt; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Notification that = (Notification) obj;
        return notificationId.equals(that.notificationId);
    }

    @Override
    public int hashCode() {
        return notificationId.hashCode();
    }
}

/**
 * NotificationType Enum
 * Bildirim türlerini tanımlar
 */
enum NotificationType {
    LIKE("Beğeni"),
    COMMENT("Yorum"), 
    FOLLOW("Takip"),
    MENTION("Etiketleme"),
    POST_CREATED("Gönderi Oluşturuldu"),
    SYSTEM("Sistem");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
