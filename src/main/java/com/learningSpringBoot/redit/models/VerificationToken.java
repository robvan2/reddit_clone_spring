package com.learningSpringBoot.redit.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

/**
 * Created by pc on 11/10/2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String token;
    private Instant expiryDate;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
