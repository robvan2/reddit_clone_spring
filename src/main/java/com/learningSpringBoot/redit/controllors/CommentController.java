package com.learningSpringBoot.redit.controllors;

import com.learningSpringBoot.redit.dtos.CommentDto;
import com.learningSpringBoot.redit.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created");
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(id));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUser(username));
    }
}
