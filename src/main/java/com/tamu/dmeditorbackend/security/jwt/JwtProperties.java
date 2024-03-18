package com.tamu.dmeditorbackend.security.jwt;


import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtProperties {
    private String secretKey = "SomeSecretKeyWhichIsVeryLongAndSecureItWillBeUsed";
}
