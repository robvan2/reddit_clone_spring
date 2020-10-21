package com.learningSpringBoot.redit.mapper;

import com.learningSpringBoot.redit.dtos.SubredditDto;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.Subreddit;
import com.learningSpringBoot.redit.models.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(source = "user.username",target = "userName")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "user",source = "user")
    Subreddit mapDtoToSubreddit(SubredditDto subreddit, User user);

}
