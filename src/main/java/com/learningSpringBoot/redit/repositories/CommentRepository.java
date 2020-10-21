package com.learningSpringBoot.redit.repositories;

import com.learningSpringBoot.redit.dtos.CommentDto;
import com.learningSpringBoot.redit.models.Comment;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pc on 11/10/2020.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUser(User user);

    Long countByPost(Post post);
}
