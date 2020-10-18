package com.learningSpringBoot.redit.controllors;

import com.learningSpringBoot.redit.dtos.PostDto;
import com.learningSpringBoot.redit.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAll(){
        return new ResponseEntity<>(postService.getAll(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostDto postDto){
        postService.createPost(postDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }
    @GetMapping("/by-subreddit/{subreddit}")
    public ResponseEntity<List<PostDto>> getPostsBySubreddit(@PathVariable Long subreddit){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(subreddit));
    }
    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUser(username));
    }
}
