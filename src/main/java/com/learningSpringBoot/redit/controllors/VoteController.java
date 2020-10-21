package com.learningSpringBoot.redit.controllors;

import com.learningSpringBoot.redit.dtos.VoteDto;
import com.learningSpringBoot.redit.exceptions.PostNotFoundException;
import com.learningSpringBoot.redit.exceptions.SpringRedditException;
import com.learningSpringBoot.redit.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
        try{
            voteService.vote(voteDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SpringRedditException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Already "+voteDto.getVoteType().toString()
                    +"D that post",exception);
        }catch (PostNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found",exception);
        }

    }
}
