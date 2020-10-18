package com.learningSpringBoot.redit.controllors;

import com.learningSpringBoot.redit.dtos.SubredditDto;
import com.learningSpringBoot.redit.services.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubReddit(@RequestBody SubredditDto subRedditDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subRedditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubReddits(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubredditById(id));
    }
}
