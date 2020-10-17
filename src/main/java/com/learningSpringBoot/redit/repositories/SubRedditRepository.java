package com.learningSpringBoot.redit.repositories;

import com.learningSpringBoot.redit.models.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pc on 11/10/2020.
 */
@Repository
public interface SubRedditRepository extends JpaRepository<SubReddit,Long> {
}
