package com.learningSpringBoot.redit.mapper;

import com.learningSpringBoot.redit.dtos.PostDto;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.Subreddit;
import com.learningSpringBoot.redit.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "postName",target = "name")
    @Mapping(source = "postId",target = "id")
    @Mapping(source = "subReddit.name",target = "subredditName")
    @Mapping(source = "user.username",target = "userName")
    PostDto mapToDto(Post post);

    @Mapping(source = "postDto.name",target = "postName")
    @Mapping(source = "postDto.description",target = "description")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(source = "subReddit", target = "subReddit")
    @Mapping(source = "user", target = "user")
    Post mapDtoToPost(PostDto postDto, Subreddit subReddit, User user);
}
