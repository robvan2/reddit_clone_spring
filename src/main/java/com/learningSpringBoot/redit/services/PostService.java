package com.learningSpringBoot.redit.services;

import com.learningSpringBoot.redit.dtos.PostDto;
import com.learningSpringBoot.redit.exceptions.SpringRedditException;
import com.learningSpringBoot.redit.exceptions.SubredditNotFoundException;
import com.learningSpringBoot.redit.mapper.PostMapper;
import com.learningSpringBoot.redit.models.Subreddit;
import com.learningSpringBoot.redit.models.User;
import com.learningSpringBoot.redit.repositories.PostRepository;
import com.learningSpringBoot.redit.repositories.SubRedditRepository;
import com.learningSpringBoot.redit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final SubRedditRepository subredditRepository;
    private final AuthService authService;
    private final UserRepository userReposertory;

    public List<PostDto> getAll() {
        return postRepository.findAll().stream().map(
              postMapper::mapToDto
        ).collect(Collectors.toList());
    }

    public void createPost(PostDto postDto) {
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName()).orElseThrow(
                ()->new SubredditNotFoundException("Can't find a subreddit with the given name")
        );
        User user = authService.getCurentUser();
        postRepository.save(postMapper.mapDtoToPost(postDto,subreddit,user));
    }

    public PostDto getPost(Long id) {
        return postMapper.mapToDto(postRepository.findById(id).orElseThrow(
                () -> new SpringRedditException("Post not found (exception occured when retriving " +
                        "post by id)")
        ));
    }

    public List<PostDto> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(
                ()->new SubredditNotFoundException("Exception occured while getting posts")
        );
        List<PostDto> postDtos = postRepository.findAllBySubReddit(subreddit)
                .stream().map(postMapper::mapToDto)
                .collect(Collectors.toList());
        return postDtos;
    }

    public List<PostDto> getPostsByUser(String username) {
        User user = userReposertory.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(username)
        );
        return postRepository.findAllByUser(user).stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
