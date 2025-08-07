// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.user.domain.model;

import com.example.user.domain.event.UserCreatedEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User Entity - Aggregate Root
 * Kullanıcı domain nesnesini temsil eder
 */
public class User {
    private final String userId;
    private Username username;
    private Email email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String bio;
    private String profileImageUrl;
    private boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private final List<String> followers;
    private final List<String> following;
    private final List<String> domainEvents;

    public User(String username, String email, String passwordHash, String firstName, String lastName) {
        this.userId = UUID.randomUUID().toString();
        this.username = new Username(username);
        this.email = new Email(email);
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = "";
        this.profileImageUrl = "";
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.domainEvents = new ArrayList<>();
        
        // Domain event tetikleme
        addDomainEvent(new UserCreatedEvent(this.userId, this.username.getValue(), this.email.getValue()));
    }

    // Profile güncelleme
    public void updateProfile(String firstName, String lastName, String bio, String profileImageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    // Şifre güncelleme
    public void updatePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
    }

    // Email güncelleme
    public void updateEmail(String newEmail) {
        this.email = new Email(newEmail);
    }

    // Username güncelleme
    public void updateUsername(String newUsername) {
        this.username = new Username(newUsername);
    }

    // Takip etme
    public void follow(String targetUserId) {
        if (!following.contains(targetUserId) && !targetUserId.equals(this.userId)) {
            following.add(targetUserId);
        }
    }

    // Takibi bırakma
    public void unfollow(String targetUserId) {
        following.remove(targetUserId);
    }

    // Takipçi ekleme (diğer kullanıcı tarafından follow edildiğinde)
    public void addFollower(String followerUserId) {
        if (!followers.contains(followerUserId) && !followerUserId.equals(this.userId)) {
            followers.add(followerUserId);
        }
    }

    // Takipçi çıkarma
    public void removeFollower(String followerUserId) {
        followers.remove(followerUserId);
    }

    // Login zamanını güncelle
    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    // Hesabı aktif/pasif yapma
    public void setActive(boolean active) {
        this.isActive = active;
    }

    private void addDomainEvent(UserCreatedEvent event) {
        this.domainEvents.add(event.toString());
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username.getValue(); }
    public String getEmail() { return email.getValue(); }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBio() { return bio; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public boolean isActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public List<String> getFollowers() { return new ArrayList<>(followers); }
    public List<String> getFollowing() { return new ArrayList<>(following); }
    public List<String> getDomainEvents() { return new ArrayList<>(domainEvents); }
    
    public int getFollowerCount() { return followers.size(); }
    public int getFollowingCount() { return following.size(); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
