// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.tagging.application;

import com.example.tagging.domain.model.Tag;
import com.example.tagging.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaggingCommandHandler {

    @Autowired
    private TagRepository tagRepository;

    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#(\\w+)");
    private static final Pattern MENTION_PATTERN = Pattern.compile("@(\\w+)");

    public List<Tag> extractTags(String content, String postId, String userId) {
        List<Tag> tags = new ArrayList<>();

        // Hashtag'leri çıkar
        Matcher hashtagMatcher = HASHTAG_PATTERN.matcher(content);
        while (hashtagMatcher.find()) {
            String hashtag = hashtagMatcher.group(1);
            Tag tag = new Tag(hashtag, "HASHTAG", postId, userId);
            tags.add(tag);
            tagRepository.save(tag);
        }

        // Mention'ları çıkar
        Matcher mentionMatcher = MENTION_PATTERN.matcher(content);
        while (mentionMatcher.find()) {
            String username = mentionMatcher.group(1);
            Tag tag = new Tag(username, "MENTION", postId, userId);
            tags.add(tag);
            tagRepository.save(tag);
        }

        return tags;
    }

    public List<String> getTagSuggestions(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Basit bir öneri sistemi
        // Gerçek uygulamada daha gelişmiş algoritma kullanılır
        List<Tag> popularTags = tagRepository.findTrendingHashtags();
        List<String> suggestions = new ArrayList<>();

        String lowerQuery = query.toLowerCase();
        for (Tag tag : popularTags) {
            if (tag.getName().toLowerCase().startsWith(lowerQuery)) {
                suggestions.add(tag.getName());
                if (suggestions.size() >= 10) break;
            }
        }

        return suggestions;
    }

    public void followHashtag(String userId, String hashtag) {
        // Hashtag takip etme işlemi
        // Bu örnekte basit bir implementasyon
        // Gerçek uygulamada ayrı bir tablo kullanılır
        tagRepository.followHashtag(userId, hashtag);
    }

    public void unfollowHashtag(String userId, String hashtag) {
        // Hashtag takipten çıkarma işlemi
        tagRepository.unfollowHashtag(userId, hashtag);
    }

    public List<String> getTrendingHashtags() {
        List<Tag> trendingTags = tagRepository.findTrendingHashtags();
        return trendingTags.stream()
                .map(Tag::getName)
                .toList();
    }

    public List<String> getPostsByHashtag(String hashtag) {
        return tagRepository.findPostIdsByHashtag(hashtag);
    }

    public List<String> getPostsByMention(String username) {
        return tagRepository.findPostIdsByMention(username);
    }
} 