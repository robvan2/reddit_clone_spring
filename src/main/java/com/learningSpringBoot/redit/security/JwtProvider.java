package com.learningSpringBoot.redit.security;

import com.learningSpringBoot.redit.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    private JwtParser parser;
    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            InputStream resourceStream = getClass().getResourceAsStream("/spring_reddit.p12");
            keyStore.load(resourceStream,"02121997".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new SpringRedditException("Exception Occured while loading the keystore");
        }

        parser = Jwts.parserBuilder().setSigningKey(getPublicKey()).build();

    }
    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    public boolean valideToken(String jwt){
        parser.parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJwt(String jwt){
        Claims claims = parser.parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("spring_reddit").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Exception Occured while retriving public key from keystore");
        }
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("spring_reddit","02121997".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception Occured while retriving private key from keystore");
        }
    }

}
