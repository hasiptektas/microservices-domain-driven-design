// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.notification.infrastructure;

import com.example.notification.application.NotificationCommandHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private NotificationCommandHandler notificationCommandHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.post-liked}", groupId = "notification-service")
    public void handlePostLikedEvent(String message) {
        try {
            JsonNode event = objectMapper.readTree(message);
            String postId = event.get("postId").asText();
            String userId = event.get("userId").asText();
            String likerId = event.get("likerId").asText();
            
            notificationCommandHandler.sendLikeNotification(userId, postId, likerId);
        } catch (Exception e) {
            // Log error and handle gracefully
        }
    }

    @KafkaListener(topics = "${kafka.topic.post-commented}", groupId = "notification-service")
    public void handlePostCommentedEvent(String message) {
        try {
            JsonNode event = objectMapper.readTree(message);
            String postId = event.get("postId").asText();
            String userId = event.get("userId").asText();
            String commenterId = event.get("commenterId").asText();
            
            notificationCommandHandler.sendCommentNotification(userId, postId, commenterId);
        } catch (Exception e) {
            // Log error and handle gracefully
        }
    }

    @KafkaListener(topics = "${kafka.topic.user-followed}", groupId = "notification-service")
    public void handleUserFollowedEvent(String message) {
        try {
            JsonNode event = objectMapper.readTree(message);
            String userId = event.get("userId").asText();
            String followerId = event.get("followerId").asText();
            
            notificationCommandHandler.sendFollowNotification(userId, followerId);
        } catch (Exception e) {
            // Log error and handle gracefully
        }
    }

    @KafkaListener(topics = "${kafka.topic.user-mentioned}", groupId = "notification-service")
    public void handleUserMentionedEvent(String message) {
        try {
            JsonNode event = objectMapper.readTree(message);
            String postId = event.get("postId").asText();
            String mentionedUserId = event.get("mentionedUserId").asText();
            String mentionerId = event.get("mentionerId").asText();
            
            notificationCommandHandler.sendMentionNotification(mentionedUserId, postId, mentionerId);
        } catch (Exception e) {
            // Log error and handle gracefully
        }
    }
} 