package com.learningSpringBoot.redit.services;

import com.learningSpringBoot.redit.dtos.VoteDto;
import com.learningSpringBoot.redit.exceptions.PostNotFoundException;
import com.learningSpringBoot.redit.exceptions.SpringRedditException;
import com.learningSpringBoot.redit.exceptions.VoteNotFoundException;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.User;
import com.learningSpringBoot.redit.models.Vote;
import com.learningSpringBoot.redit.models.VoteType;
import com.learningSpringBoot.redit.repositories.PostRepository;
import com.learningSpringBoot.redit.repositories.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {
    private final AuthService authService;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    public void vote(VoteDto voteDto) {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(
                ()->new PostNotFoundException("Post not found when voting postId = "+voteDto.getPostId())
        );
        Vote vote = voteRepository.findTopByUserAndPostOrderByVoteIdDesc(user,post).orElse(
                Vote.builder().post(post).user(user).build()
        );
        if(vote.getVoteType() != null && vote.getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already "+voteDto.getVoteType()+" this post");
        }else {
            vote.setVoteType(voteDto.getVoteType());
        }

        if(VoteType.UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapDtoToVote(voteDto , post));
        postRepository.save(post);
    }

    private Vote mapDtoToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .post(post)
                .user(authService.getCurrentUser())
                .voteType(voteDto.getVoteType())
                .build();
    }
}
