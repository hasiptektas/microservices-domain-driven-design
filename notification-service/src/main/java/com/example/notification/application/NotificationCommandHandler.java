package com.example.notification.application;

import com.example.notification.domain.model.Notification;
import com.example.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationCommandHandler {

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendNotification(String userId, String type, String title, String message, String relatedId) {
        Notification notification = new Notification(
            UUID.randomUUID().toString(),
            userId,
            type,
            title,
            message,
            relatedId,
            false,
            LocalDateTime.now()
        );
        
        notificationRepository.save(notification);
    }

    public void markAsRead(String notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    public void markAllAsRead(String userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
        unreadNotifications.forEach(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    public void deleteNotification(String notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public void deleteAllNotifications(String userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        notificationRepository.deleteAll(notifications);
    }

    public void sendLikeNotification(String userId, String postId, String likerId) {
        sendNotification(
            userId,
            "LIKE",
            "Yeni beğeni",
            "Postunuzu beğendi",
            postId
        );
    }

    public void sendCommentNotification(String userId, String postId, String commenterId) {
        sendNotification(
            userId,
            "COMMENT",
            "Yeni yorum",
            "Postunuza yorum yaptı",
            postId
        );
    }

    public void sendFollowNotification(String userId, String followerId) {
        sendNotification(
            userId,
            "FOLLOW",
            "Yeni takipçi",
            "Sizi takip etmeye başladı",
            followerId
        );
    }

    public void sendMentionNotification(String userId, String postId, String mentionerId) {
        sendNotification(
            userId,
            "MENTION",
            "Etiketlendiniz",
            "Bir postta etiketlendiniz",
            postId
        );
    }
} 