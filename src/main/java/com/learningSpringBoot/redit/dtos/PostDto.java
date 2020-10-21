package com.learningSpringBoot.redit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String name;
    private String description;
    private String url;
    private String subredditName;
    private String userName;
    private Integer voteCount;
    private Integer commentCount;
    private String created;
}
