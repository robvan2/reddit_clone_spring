package com.learningSpringBoot.redit.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogingRequest {
    private String username;
    private String Password;
}
