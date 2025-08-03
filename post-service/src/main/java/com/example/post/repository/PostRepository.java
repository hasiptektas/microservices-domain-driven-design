package com.example.post.repository;

import com.example.post.domain.model.Post;
import java.util.List;
import java.util.Optional;

/**
 * PostRepository Interface
 * Post Aggregate'inin veri erişim katmanı soyutlaması
 */
public interface PostRepository {
    
    /**
     * Yeni gönderi kaydet
     * @param post Kaydedilecek gönderi
     * @return Kaydedilen gönderi
     */
    Post save(Post post);
    
    /**
     * ID'ye göre gönderi bul
     * @param postId Gönderi ID'si
     * @return Optional Post
     */
    Optional<Post> findById(String postId);
    
    /**
     * Kullanıcının tüm gönderilerini getir
     * @param userId Kullanıcı ID'si
     * @return Kullanıcının gönderi listesi
     */
    List<Post> findByUserId(String userId);
    
    /**
     * Tüm gönderileri sayfalı olarak getir
     * @param page Sayfa numarası
     * @param size Sayfa boyutu
     * @return Gönderi listesi
     */
    List<Post> findAll(int page, int size);
    
    /**
     * Gönderi sil
     * @param postId Silinecek gönderi ID'si
     */
    void deleteById(String postId);
    
    /**
     * Belirli bir tarih aralığındaki gönderileri getir
     * @param userId Kullanıcı ID'si
     * @param startDate Başlangıç tarihi (ISO format)
     * @param endDate Bitiş tarihi (ISO format)
     * @return Gönderi listesi
     */
    List<Post> findByUserIdAndDateRange(String userId, String startDate, String endDate);
    
    /**
     * Caption'da belirli bir kelime içeren gönderileri bul
     * @param keyword Aranacak kelime
     * @return Gönderi listesi
     */
    List<Post> findByCaptionContaining(String keyword);
}
