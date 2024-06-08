package com.sr.projects.auth_service.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.sr.projects.auth_service.security.interfaces.PasswordHash;

public class PBKDF2Impl implements PasswordHash { 
    SecureRandom random = new SecureRandom();
    private final String salt = "random";

    /**
     * 
     * @param text
     * @param secret
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String generateHash(String text) {
        KeySpec spec = new PBEKeySpec(text.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            return enc.encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
