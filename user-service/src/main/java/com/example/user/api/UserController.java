// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.user.api;

import com.example.user.application.UserCommandHandler;
import com.example.user.domain.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserCommandHandler userCommandHandler;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest request) {
        try {
            String userId = userCommandHandler.registerUser(
                request.getUsername(), 
                request.getEmail(), 
                request.getPassword()
            );
            return ResponseEntity.ok(new RegisterUserResponse(userId, "Kullanıcı başarıyla kaydedildi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            String token = userCommandHandler.loginUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(token, "Giriş başarılı"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String userId, @RequestBody UpdateUserRequest request) {
        try {
            userCommandHandler.updateUserProfile(userId, request.getUsername(), request.getBio());
            return ResponseEntity.ok(new SuccessResponse("Profil başarıyla güncellendi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<?> followUser(@PathVariable String userId, @PathVariable String targetUserId) {
        try {
            userCommandHandler.followUser(userId, targetUserId);
            return ResponseEntity.ok(new SuccessResponse("Kullanıcı takip edildi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/unfollow/{targetUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable String userId, @PathVariable String targetUserId) {
        try {
            userCommandHandler.unfollowUser(userId, targetUserId);
            return ResponseEntity.ok(new SuccessResponse("Kullanıcı takipten çıkarıldı"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<String>> getFollowers(@PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getFollowers());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<String>> getFollowing(@PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getFollowing());
        }
        return ResponseEntity.notFound().build();
    }

    // Request/Response DTOs
    public static class RegisterUserRequest {
        private String username;
        private String email;
        private String password;

        // Getters and Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterUserResponse {
        private String userId;
        private String message;

        public RegisterUserResponse(String userId, String message) {
            this.userId = userId;
            this.message = message;
        }

        public String getUserId() { return userId; }
        public String getMessage() { return message; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private String message;

        public LoginResponse(String token, String message) {
            this.token = token;
            this.message = message;
        }

        public String getToken() { return token; }
        public String getMessage() { return message; }
    }

    public static class UpdateUserRequest {
        private String username;
        private String bio;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getBio() { return bio; }
        public void setBio(String bio) { this.bio = bio; }
    }

    public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
    }

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { return error; }
    }
} 