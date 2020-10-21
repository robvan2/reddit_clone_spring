package com.learningSpringBoot.redit.dtos;

import com.learningSpringBoot.redit.models.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
