package com.learningSpringBoot.redit.services;

import com.learningSpringBoot.redit.dtos.SubredditDto;
import com.learningSpringBoot.redit.exceptions.SpringRedditException;
import com.learningSpringBoot.redit.mapper.SubredditMapper;
import com.learningSpringBoot.redit.models.Subreddit;
import com.learningSpringBoot.redit.repositories.SubRedditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubRedditRepository subRedditRepository;
    private final SubredditMapper subRedditMapper;
    private final AuthService authService;

    @Transactional
    public SubredditDto save(SubredditDto subRedditDto){
        Subreddit subReddit = subRedditRepository.save(
                subRedditMapper.mapDtoToSubreddit(subRedditDto,authService.getCurrentUser())
        );
        subRedditDto.setId(subReddit.getId());
        return subRedditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subRedditRepository.findAll().stream()
                .map(subRedditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubredditById(Long id) {
        return subRedditMapper.mapSubredditToDto(subRedditRepository.findById(id).orElseThrow(
                () -> new SpringRedditException("Subbreddit not found (exception occured when retriving " +
                        "subreddit by id)")
        ));
    }
//    private SubReddit mapSubRedditDto(SubRedditDto subRedditDto) {
//        return  SubReddit.builder().name(subRedditDto.getName())
//                .description(subRedditDto.getDescription())
//                .build();
//    }



//    private SubRedditDto mapToDto(SubReddit subReddit) {
//        return SubRedditDto.builder()
//                .name(subReddit.getName())
//                .description(subReddit.getDescription())
//                .id(subReddit.getId())
//                .numberOfPosts(subReddit.getPosts().size())
//                .build();
//    }
}
