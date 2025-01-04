package com.lessons.spring_security.service;

import com.lessons.spring_security.entity.RefreshToken;
import com.lessons.spring_security.repository.RefreshTokenRepository;
import com.lessons.spring_security.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public RefreshToken createRefreshToken(String name){
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfoRepository.findByName(name).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(60000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
          throw new RuntimeException((refreshToken.getToken() + "Refresh Token already expired please sign in again"));
        }
        return refreshToken;
    }
}
