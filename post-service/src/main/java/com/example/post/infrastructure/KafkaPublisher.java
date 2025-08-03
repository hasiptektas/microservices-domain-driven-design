package com.example.post.infrastructure;

import com.example.post.domain.event.UserMentionedEvent;

/**
 * KafkaPublisher - Infrastructure Service
 * Kafka üzerinden event yayınlama işlemlerini yürütür
 */
public class KafkaPublisher {
    
    /**
     * PostCreatedEvent'i Kafka'ya yayınla
     * @param event Yayınlanacak event (string format)
     */
    public void publishPostCreatedEvent(String event) {
        // Gerçek implementasyonda Kafka producer kullanılacak
        System.out.println("Kafka'ya PostCreatedEvent yayınlandı: " + event);
        
        // Örnek Kafka implementasyonu:
        // kafkaTemplate.send("post-created-topic", event);
    }
    
    /**
     * UserMentionedEvent'i Kafka'ya yayınla
     * @param event Yayınlanacak event
     */
    public void publishUserMentionedEvent(UserMentionedEvent event) {
        // Gerçek implementasyonda Kafka producer kullanılacak
        System.out.println("Kafka'ya UserMentionedEvent yayınlandı: " + event.toString());
        
        // Örnek Kafka implementasyonu:
        // kafkaTemplate.send("user-mentioned-topic", event);
    }
    
    /**
     * Generic event yayınlama metodu
     * @param topic Kafka topic'i
     * @param event Event objesi
     */
    public void publishEvent(String topic, Object event) {
        System.out.println("Kafka topic '" + topic + "' üzerine event yayınlandı: " + event.toString());
        
        // Gerçek implementasyonda:
        // kafkaTemplate.send(topic, event);
    }
}
