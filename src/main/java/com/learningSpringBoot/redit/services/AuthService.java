package com.learningSpringBoot.redit.services;

import com.learningSpringBoot.redit.dtos.*;
import com.learningSpringBoot.redit.exceptions.SpringRedditException;
import com.learningSpringBoot.redit.models.User;
import com.learningSpringBoot.redit.models.VerificationToken;
import com.learningSpringBoot.redit.repositories.UserRepository;
import com.learningSpringBoot.redit.repositories.VerificationTokenRepository;
import com.learningSpringBoot.redit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendEmail(new NotificationEmail("Please activate your account",user.getEmail(),
                "Thank you for signing up to Reddit clone please click on the link bellow to verify your account" +
                        "http://localhost:8080/api/auth/accountVerification/"+token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()-> new SpringRedditException("invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new SpringRedditException("user not found with name : "+username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LogingRequest logingRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                logingRequest.getUsername(),logingRequest.getPassword()
        ));
        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            return AuthenticationResponse.builder()
                    .username(logingRequest.getUsername())
                    .authToken(token)
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiredAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                    .build();
        }
        return null;
    }

    public User getCurrentUser() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()->new SpringRedditException("User not found with username = "+user.getUsername())
        );
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenByUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .authToken(token)
                .username(refreshTokenRequest.getUsername())
                .expiredAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .build();
    }
}
