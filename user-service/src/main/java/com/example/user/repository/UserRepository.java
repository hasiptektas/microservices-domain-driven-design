package com.example.user.repository;

import com.example.user.domain.model.User;
import java.util.List;
import java.util.Optional;

/**
 * UserRepository Interface
 * User Aggregate'inin veri erişim katmanı soyutlaması
 */
public interface UserRepository {
    
    /**
     * Yeni kullanıcı kaydet
     * @param user Kaydedilecek kullanıcı
     * @return Kaydedilen kullanıcı
     */
    User save(User user);
    
    /**
     * ID'ye göre kullanıcı bul
     * @param userId Kullanıcı ID'si
     * @return Optional User
     */
    Optional<User> findById(String userId);
    
    /**
     * Kullanıcı adına göre kullanıcı bul
     * @param username Kullanıcı adı
     * @return Optional User
     */
    Optional<User> findByUsername(String username);
    
    /**
     * E-posta adresine göre kullanıcı bul
     * @param email E-posta adresi
     * @return Optional User
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Kullanıcı adının mevcut olup olmadığını kontrol et
     * @param username Kontrol edilecek kullanıcı adı
     * @return Kullanıcı adı mevcut mu?
     */
    boolean existsByUsername(String username);
    
    /**
     * E-posta adresinin mevcut olup olmadığını kontrol et
     * @param email Kontrol edilecek e-posta adresi
     * @return E-posta mevcut mu?
     */
    boolean existsByEmail(String email);
    
    /**
     * Kullanıcı sil
     * @param userId Silinecek kullanıcı ID'si
     */
    void deleteById(String userId);
    
    /**
     * Tüm kullanıcıları sayfalı olarak getir
     * @param page Sayfa numarası
     * @param size Sayfa boyutu
     * @return Kullanıcı listesi
     */
    List<User> findAll(int page, int size);
    
    /**
     * İsim veya kullanıcı adına göre arama yap
     * @param searchTerm Arama terimi
     * @return Kullanıcı listesi
     */
    List<User> searchUsers(String searchTerm);
    
    /**
     * Belirli bir kullanıcının takipçilerini getir
     * @param userId Kullanıcı ID'si
     * @return Takipçi kullanıcı listesi
     */
    List<User> findFollowers(String userId);
    
    /**
     * Belirli bir kullanıcının takip ettiklerini getir
     * @param userId Kullanıcı ID'si
     * @return Takip edilen kullanıcı listesi
     */
    List<User> findFollowing(String userId);
    
    /**
     * Aktif kullanıcıları getir
     * @return Aktif kullanıcı listesi
     */
    List<User> findActiveUsers();
}
