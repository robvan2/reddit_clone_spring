package com.learningSpringBoot.redit.repositories;

import com.learningSpringBoot.redit.dtos.PostDto;
import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.Subreddit;
import com.learningSpringBoot.redit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pc on 11/10/2020.
 */
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubReddit(Subreddit subReddit);
    List<Post> findAllByUser(User user);
}
