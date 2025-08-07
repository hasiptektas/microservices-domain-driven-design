// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.user.application;

import com.example.user.domain.model.User;
import com.example.user.domain.service.PasswordService;
import com.example.user.repository.UserRepository;
import java.util.Optional;

/**
 * UserCommandHandler - Application Service
 * User ile ilgili komutları işleyen uygulama servisi
 */
public class UserCommandHandler {
    
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    
    public UserCommandHandler(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }
    
    /**
     * Yeni kullanıcı kaydet
     * @param username Kullanıcı adı
     * @param email E-posta adresi
     * @param password Şifre
     * @param firstName Ad
     * @param lastName Soyad
     * @return Oluşturulan kullanıcı ID'si
     */
    public String registerUser(String username, String email, String password, String firstName, String lastName) {
        // Kullanıcı adı kontrolü
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten mevcut: " + username);
        }
        
        // E-posta kontrolü
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Bu e-posta adresi zaten mevcut: " + email);
        }
        
        // Şifre güçlülük kontrolü
        if (!passwordService.isPasswordStrong(password)) {
            throw new IllegalArgumentException("Şifre yeterince güçlü değil. En az 8 karakter, büyük/küçük harf, rakam ve özel karakter içermelidir.");
        }
        
        // Şifreyi hash'le
        String hashedPassword = passwordService.hashPassword(password);
        
        // User domain object oluştur
        User user = new User(username, email, hashedPassword, firstName, lastName);
        
        // Repository'ye kaydet
        User savedUser = userRepository.save(user);
        
        return savedUser.getUserId();
    }
    
    /**
     * Kullanıcı girişi
     * @param usernameOrEmail Kullanıcı adı veya e-posta
     * @param password Şifre
     * @return Giriş yapan kullanıcı ID'si
     */
    public String loginUser(String usernameOrEmail, String password) {
        Optional<User> userOpt;
        
        // E-posta formatında mı kontrol et
        if (usernameOrEmail.contains("@")) {
            userOpt = userRepository.findByEmail(usernameOrEmail);
        } else {
            userOpt = userRepository.findByUsername(usernameOrEmail);
        }
        
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("Kullanıcı bulunamadı");
        }
        
        User user = userOpt.get();
        
        if (!user.isActive()) {
            throw new IllegalArgumentException("Hesap pasif durumda");
        }
        
        if (!passwordService.verifyPassword(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Geçersiz şifre");
        }
        
        // Login zamanını güncelle
        user.updateLastLogin();
        userRepository.save(user);
        
        return user.getUserId();
    }
    
    /**
     * Profil güncelle
     * @param userId Kullanıcı ID'si
     * @param firstName Ad
     * @param lastName Soyad
     * @param bio Biyografi
     * @param profileImageUrl Profil resmi URL'si
     * @return Başarı durumu
     */
    public boolean updateProfile(String userId, String firstName, String lastName, String bio, String profileImageUrl) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.updateProfile(firstName, lastName, bio, profileImageUrl);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
    
    /**
     * Şifre değiştir
     * @param userId Kullanıcı ID'si
     * @param currentPassword Mevcut şifre
     * @param newPassword Yeni şifre
     * @return Başarı durumu
     */
    public boolean changePassword(String userId, String currentPassword, String newPassword) {
        return userRepository.findById(userId)
                .map(user -> {
                    // Mevcut şifreyi doğrula
                    if (!passwordService.verifyPassword(currentPassword, user.getPasswordHash())) {
                        throw new IllegalArgumentException("Mevcut şifre yanlış");
                    }
                    
                    // Yeni şifre güçlülük kontrolü
                    if (!passwordService.isPasswordStrong(newPassword)) {
                        throw new IllegalArgumentException("Yeni şifre yeterince güçlü değil");
                    }
                    
                    // Yeni şifreyi hash'le ve güncelle
                    String newHashedPassword = passwordService.hashPassword(newPassword);
                    user.updatePassword(newHashedPassword);
                    userRepository.save(user);
                    
                    return true;
                })
                .orElse(false);
    }
    
    /**
     * Kullanıcı takip et
     * @param followerId Takip eden kullanıcı ID'si
     * @param targetUserId Takip edilecek kullanıcı ID'si
     * @return Başarı durumu
     */
    public boolean followUser(String followerId, String targetUserId) {
        if (followerId.equals(targetUserId)) {
            throw new IllegalArgumentException("Kendini takip edemezsin");
        }
        
        Optional<User> followerOpt = userRepository.findById(followerId);
        Optional<User> targetOpt = userRepository.findById(targetUserId);
        
        if (followerOpt.isPresent() && targetOpt.isPresent()) {
            User follower = followerOpt.get();
            User target = targetOpt.get();
            
            follower.follow(targetUserId);
            target.addFollower(followerId);
            
            userRepository.save(follower);
            userRepository.save(target);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Kullanıcı takibi bırak
     * @param followerId Takibi bırakan kullanıcı ID'si
     * @param targetUserId Takibi bırakılacak kullanıcı ID'si
     * @return Başarı durumu
     */
    public boolean unfollowUser(String followerId, String targetUserId) {
        Optional<User> followerOpt = userRepository.findById(followerId);
        Optional<User> targetOpt = userRepository.findById(targetUserId);
        
        if (followerOpt.isPresent() && targetOpt.isPresent()) {
            User follower = followerOpt.get();
            User target = targetOpt.get();
            
            follower.unfollow(targetUserId);
            target.removeFollower(followerId);
            
            userRepository.save(follower);
            userRepository.save(target);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Hesabı aktif/pasif yap
     * @param userId Kullanıcı ID'si
     * @param active Aktiflik durumu
     * @return Başarı durumu
     */
    public boolean setUserActive(String userId, boolean active) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setActive(active);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
    
    /**
     * Kullanıcı sil
     * @param userId Kullanıcı ID'si
     * @return Başarı durumu
     */
    public boolean deleteUser(String userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    
    /**
     * Şifre sıfırlama için rastgele şifre oluştur
     * @param userId Kullanıcı ID'si
     * @return Yeni şifre
     */
    public String resetPassword(String userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    String newPassword = passwordService.generateRandomPassword(12);
                    String hashedPassword = passwordService.hashPassword(newPassword);
                    user.updatePassword(hashedPassword);
                    userRepository.save(user);
                    return newPassword;
                })
                .orElse(null);
    }
}
