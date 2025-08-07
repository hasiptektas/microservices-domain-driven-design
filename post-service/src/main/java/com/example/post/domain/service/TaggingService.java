// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.post.domain.service;

import com.example.post.domain.event.UserMentionedEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TaggingService - Domain Service
 * Gönderi içeriğindeki '@' işaretli kullanıcı etiketlerini algılar
 */
public class TaggingService {
    private static final Pattern MENTION_PATTERN = Pattern.compile("@([a-zA-Z0-9_]+)");

    /**
     * Gönderi içeriğindeki kullanıcı etiketlerini algılar
     * @param caption Gönderi açıklaması
     * @return Etiketlenen kullanıcı adları listesi
     */
    public List<String> extractMentions(String caption) {
        List<String> mentions = new ArrayList<>();
        if (caption == null || caption.trim().isEmpty()) {
            return mentions;
        }

        Matcher matcher = MENTION_PATTERN.matcher(caption);
        while (matcher.find()) {
            String username = matcher.group(1);
            if (!mentions.contains(username)) {
                mentions.add(username);
            }
        }
        return mentions;
    }

    /**
     * Etiketleme algılandığında UserMentionedEvent oluşturur
     * @param postId Gönderi ID'si
     * @param mentioningUserId Etiketleyen kullanıcı ID'si
     * @param caption Gönderi açıklaması
     * @return UserMentionedEvent veya null (etiket yoksa)
     */
    public UserMentionedEvent createMentionEventIfExists(String postId, String mentioningUserId, String caption) {
        List<String> mentionedUsernames = extractMentions(caption);
        
        if (!mentionedUsernames.isEmpty()) {
            // Gerçek implementasyonda username'leri userId'lere çevirmek gerekir
            // Şimdilik username'leri userId olarak kullanıyoruz
            return new UserMentionedEvent(postId, mentioningUserId, mentionedUsernames, caption);
        }
        
        return null;
    }

    /**
     * Caption içinde belirli bir kullanıcının etiketlenip etiketlenmediğini kontrol eder
     * @param caption Gönderi açıklaması
     * @param username Kontrol edilecek kullanıcı adı
     * @return true eğer etiketlenmişse
     */
    public boolean isUserMentioned(String caption, String username) {
        List<String> mentions = extractMentions(caption);
        return mentions.contains(username);
    }
}
