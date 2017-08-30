package com.test.vote.repository;

import com.test.vote.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
