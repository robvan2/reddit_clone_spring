package com.learningSpringBoot.redit.repositories;

import com.learningSpringBoot.redit.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by pc on 11/10/2020.
 */
@Repository
public interface SubRedditRepository extends JpaRepository<Subreddit,Long> {
    Optional<Subreddit> findByName(String subredditName);
}
