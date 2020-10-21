package com.learningSpringBoot.redit.controllors;

import com.learningSpringBoot.redit.dtos.AuthenticationResponse;
import com.learningSpringBoot.redit.dtos.LogingRequest;
import com.learningSpringBoot.redit.dtos.RefreshTokenRequest;
import com.learningSpringBoot.redit.dtos.RegisterRequest;
import com.learningSpringBoot.redit.services.AuthService;
import com.learningSpringBoot.redit.services.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account verified successfully",HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LogingRequest logingRequest){
        return authService.login(logingRequest);
    }
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token deleted");
    }
}
