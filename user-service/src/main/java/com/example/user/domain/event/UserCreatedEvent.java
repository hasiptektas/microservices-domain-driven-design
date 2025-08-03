package com.example.user.domain.event;

import java.time.LocalDateTime;

/**
 * UserCreatedEvent - Domain Event
 * Yeni kullanıcı oluşturulduğunda tetiklenen domain event
 */
public class UserCreatedEvent {
    private final String userId;
    private final String username;
    private final String email;
    private final LocalDateTime occurredOn;

    public UserCreatedEvent(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.occurredOn = LocalDateTime.now();
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getOccurredOn() { return occurredOn; }

    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
