package com.learningSpringBoot.redit.services;

import com.learningSpringBoot.redit.dtos.CommentDto;
import com.learningSpringBoot.redit.dtos.NotificationEmail;
import com.learningSpringBoot.redit.exceptions.PostNotFoundException;
import com.learningSpringBoot.redit.mapper.CommentMapper;
import com.learningSpringBoot.redit.models.Comment;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.User;
import com.learningSpringBoot.redit.repositories.CommentRepository;
import com.learningSpringBoot.redit.repositories.PostRepository;
import com.learningSpringBoot.redit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

    private final CommentMapper commentMapper;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    public void createComment(CommentDto commentDto) {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(
                ()-> new PostNotFoundException("Couldnt find post while adding comment postId = " +
                        commentDto.getPostId().toString())
        );
        Comment comment = commentMapper.mapDtoToComment(commentDto,user,post);
        commentRepository.save(comment);

        post.setVoteCount(post.getVoteCount()+1);
        postRepository.save(post);

        if (!comment.getUser().getUsername().equals(post.getUser().getUsername())) {
            String message = comment.getUser().getUsername() + " has commented on your post : " + post.getUrl();
            sendCommentEmail(message, post.getUser(), comment.getUser().getUsername());
        }
    }

    private void sendCommentEmail(String message, User recipient, String username) {
        mailService.sendEmail(new NotificationEmail(
                username+" Commented on your post",recipient.getEmail(),message)
        );
    }

    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new PostNotFoundException("Couldn't find post while retrieving comments  postId = " +
                        postId.toString())
        );
        return commentRepository.findAllByPost(post)
                .stream().map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username)
        );
        return commentRepository.findAllByUser(user)
                .stream().map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
