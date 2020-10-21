package com.learningSpringBoot.redit.services;

import com.learningSpringBoot.redit.exceptions.SpringRedditException;
import com.learningSpringBoot.redit.models.RefreshToken;
import com.learningSpringBoot.redit.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .createdDate(Instant.now())
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token).orElseThrow(
                ()-> new SpringRedditException("Invalid refresh token")
        );
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
