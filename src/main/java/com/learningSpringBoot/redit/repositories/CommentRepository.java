package com.learningSpringBoot.redit.repositories;

import com.learningSpringBoot.redit.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pc on 11/10/2020.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
