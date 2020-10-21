package com.learningSpringBoot.redit.mapper;

import com.learningSpringBoot.redit.dtos.PostDto;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.Subreddit;
import com.learningSpringBoot.redit.models.User;
import com.learningSpringBoot.redit.repositories.CommentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Mapping(source = "postName",target = "name")
    @Mapping(source = "postId",target = "id")
    @Mapping(source = "subReddit.name",target = "subredditName")
    @Mapping(source = "user.username",target = "userName")
    @Mapping(target = "commentCount" , expression = "java(getCommentCount(post))")
    @Mapping(target = "created",source = "createdDate")
    public abstract PostDto mapToDto(Post post);

    @Mapping(source = "postDto.name",target = "postName")
    @Mapping(source = "postDto.description",target = "description")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(source = "subReddit", target = "subReddit")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post mapDtoToPost(PostDto postDto, Subreddit subReddit, User user);

    Integer getCommentCount(Post post){
        return Math.toIntExact(commentRepository.countByPost(post));
    }
}
