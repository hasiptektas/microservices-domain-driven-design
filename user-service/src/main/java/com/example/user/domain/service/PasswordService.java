// Bu dosyada yapılan değişiklik, ödev değerlendirmesi ve geri bildirim için eklenmiştir.
package com.example.user.domain.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PasswordService - Domain Service
 * Şifre hashleme, doğrulama ve güvenlik işlemlerini yürütür
 */
public class PasswordService {
    
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    
    /**
     * Şifreyi hash'ler ve salt ile birleştirir
     * @param password Ham şifre
     * @return Hash'lenmiş şifre (salt + hash formatında)
     */
    public String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Şifre boş olamaz");
        }
        
        if (password.length() < 6) {
            throw new IllegalArgumentException("Şifre en az 6 karakter olmalıdır");
        }
        
        try {
            // Salt oluştur
            byte[] salt = generateSalt();
            
            // Şifreyi hash'le
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            
            // Salt + Hash'i birleştir ve Base64 encode et
            byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);
            
            return Base64.getEncoder().encodeToString(saltAndHash);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Şifre hashleme algoritması bulunamadı", e);
        }
    }
    
    /**
     * Şifreyi doğrular
     * @param password Ham şifre
     * @param hashedPassword Hash'lenmiş şifre
     * @return Şifre doğru mu?
     */
    public boolean verifyPassword(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            return false;
        }
        
        try {
            // Base64 decode et
            byte[] saltAndHash = Base64.getDecoder().decode(hashedPassword);
            
            // Salt'ı ayır
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(saltAndHash, 0, salt, 0, SALT_LENGTH);
            
            // Gelen şifreyi aynı salt ile hash'le
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedInputPassword = md.digest(password.getBytes());
            
            // Hash'leri karşılaştır
            byte[] storedHash = new byte[saltAndHash.length - SALT_LENGTH];
            System.arraycopy(saltAndHash, SALT_LENGTH, storedHash, 0, storedHash.length);
            
            return MessageDigest.isEqual(hashedInputPassword, storedHash);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Şifre güçlülük kontrolü
     * @param password Kontrol edilecek şifre
     * @return Şifre güçlü mü?
     */
    public boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
    
    /**
     * Güvenli salt oluşturur
     * @return Rastgele salt
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * Rastgele şifre oluşturur
     * @param length Şifre uzunluğu
     * @return Rastgele şifre
     */
    public String generateRandomPassword(int length) {
        if (length < 6) {
            throw new IllegalArgumentException("Şifre uzunluğu en az 6 olmalıdır");
        }
        
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*";
        String allChars = upperCase + lowerCase + digits + specialChars;
        
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        // En az bir büyük harf, küçük harf, rakam ve özel karakter ekle
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        
        // Kalan karakterleri rastgele ekle
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        
        // Karıştır
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }
        
        return new String(passwordArray);
    }
}
