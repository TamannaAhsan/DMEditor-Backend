package com.tamu.dmeditorbackend.service;

import com.tamu.dmeditorbackend.entity.User;
import com.tamu.dmeditorbackend.repository.UserRepository;
import com.tamu.dmeditorbackend.security.config.JwtConfig;
import com.tamu.dmeditorbackend.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.tamu.dmeditorbackend.service.Util.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder encoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Override
    public User createUser(User payload) {
        payload.setPassword(encoder.encode(payload.getPassword()));
        User savedUser = userRepository.save(payload);
        savedUser.setToken(createAccessToken(savedUser));
        return savedUser;
    }

    @Override
    public User login(User payload) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(payload.getEmail());

        if (user.isEmpty())
            return null;

        Boolean match = encoder.matches(payload.getPassword(), user.get().getPassword());
        if (!match)
            return null;

        payload.setToken(createAccessToken(user.get()));
        payload.setType(user.get().getType());
        payload.setId(user.get().getId());

        return payload;
    }

    @Override
    public Boolean validateToken(String token) {
        String email = tokenProvider.getUsername(token);
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        return user.isPresent();
    }

    @Override
    public Long vendorId(String token) {
        try {
            String email = tokenProvider.getUsername(token);
            User vendor = userRepository.findByEmailIgnoreCase(email)
                    .orElseThrow(()-> new RuntimeException(""));
            return vendor.getId();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String vendorEmail(String token) {
        try {
            return tokenProvider.getUsername(token);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User updateUser(User payload, Long id) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User id not found" +id));
        copyNonNullProperties(payload,updatedUser);
        return userRepository.save(updatedUser);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User id not found" +id));
        userRepository.delete(user);
    }

    private String createAccessToken(User userLoginDTO){
        String token = Jwts.builder()
                .setSubject(userLoginDTO.getEmail())
                .claim("authorities", userLoginDTO.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(
                        LocalDate.now().plusDays(jwtConfig.getTokenExpiredAfterDays())
                ))
                .signWith(secretKey)
                .compact();
        return token;
    }

    private String createRefreshToken(User userLoginDTO){
        String token = Jwts.builder()
                .setSubject(userLoginDTO.getEmail())
                .claim("authorities", userLoginDTO.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(
                        LocalDate.now().plusDays(jwtConfig.getRefreshTokenExpiredAfterDays())
                ))
                .signWith(secretKey)
                .compact();
        return token;
    }
}
