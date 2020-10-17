package com.learningSpringBoot.redit.repositories;

import com.learningSpringBoot.redit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by pc on 11/10/2020.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
}
