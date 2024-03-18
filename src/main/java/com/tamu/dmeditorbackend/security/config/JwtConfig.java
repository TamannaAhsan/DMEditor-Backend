package com.tamu.dmeditorbackend.security.config;


import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@Getter
@Setter
public class JwtConfig {
    private String secretKey = "SomeSecretKeyWhichIsVeryLongAndSecureItWillBeUsed";
    private Integer tokenExpiredAfterDays = 7;
    private Integer refreshTokenExpiredAfterDays = 30;
    private static final int SECRET_KEY_LENGTH = 48; // Length in bytes


    @Bean
    public SecretKey secretKey() {
        if (secretKey.length() < SECRET_KEY_LENGTH) {
            throw new IllegalArgumentException("Secret key length is insufficient for HMAC SHA-384");
        }
        return Keys.hmacShaKeyFor(secretKey.getBytes());
//        return Keys.hmacShaKeyFor(secretKey.getBytes(SignatureAlgorithm.HS384.getValue()));

    }
}

