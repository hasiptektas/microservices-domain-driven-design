package com.example.user.domain.model;

import java.util.regex.Pattern;

/**
 * Username Value Object
 * Kullanıcı adı benzersizliği ve uzunluk sınırlarını kapsayan değer nesnesi
 */
public class Username {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,30}$");
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;
    
    private final String value;

    public Username(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı adı boş olamaz");
        }
        
        String trimmedUsername = username.trim().toLowerCase();
        
        if (trimmedUsername.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Kullanıcı adı en az " + MIN_LENGTH + " karakter olmalıdır");
        }
        
        if (trimmedUsername.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Kullanıcı adı en fazla " + MAX_LENGTH + " karakter olabilir");
        }
        
        if (!USERNAME_PATTERN.matcher(trimmedUsername).matches()) {
            throw new IllegalArgumentException("Kullanıcı adı sadece harf, rakam ve alt çizgi içerebilir: " + username);
        }
        
        this.value = trimmedUsername;
    }

    public String getValue() {
        return value;
    }

    public boolean isValid() {
        return USERNAME_PATTERN.matcher(value).matches() && 
               value.length() >= MIN_LENGTH && 
               value.length() <= MAX_LENGTH;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Username username = (Username) obj;
        return value.equals(username.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
