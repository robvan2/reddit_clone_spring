package com.learningSpringBoot.redit.repositories;

import com.learningSpringBoot.redit.models.Post;
import com.learningSpringBoot.redit.models.User;
import com.learningSpringBoot.redit.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by pc on 11/10/2020.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByUserAndPostOrderByVoteIdDesc(User user, Post post);
}
