package com.sr.projects.auth_service.security.interfaces;

public interface PasswordHash {
        public String generateHash(String text);
}
