package com.test.vote.services;

import com.test.vote.repository.entity.User;
import javaslang.control.Option;

import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface UserService {

    Option<User> get(Long id);

    Option<User> getByEmail(String email);

    List<User> findAll();

    User update(User entity);

    User create(User entity);

    void delete(User user);
}
