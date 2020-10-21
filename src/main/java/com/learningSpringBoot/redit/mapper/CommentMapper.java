package com.learningSpringBoot.redit.mapper;

import com.learningSpringBoot.redit.dtos.CommentDto;
import com.learningSpringBoot.redit.models.Comment;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "user",source = "user")
    @Mapping(target = "post",source = "post")
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    Comment mapDtoToComment(CommentDto commentDto, User user, Post post);

    @Mapping(target = "postId",source = "post.postId")
    @Mapping(target = "userName",source = "user.username")
    CommentDto mapToDto(Comment comment);
}
