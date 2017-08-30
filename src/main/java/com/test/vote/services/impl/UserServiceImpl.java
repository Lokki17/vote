package com.test.vote.services.impl;

import com.test.vote.repository.UserRepository;
import com.test.vote.repository.entity.User;
import com.test.vote.services.UserService;
import javaslang.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserServiceImpl implements UserService {

    @NonNull
    private UserRepository repository;


    @Override
    public Option<User> get(Long id) {
        return Option.of(repository.findOne(id));
    }

    @Override
    public Option<User> getByEmail(String email) {
        return Option.of(repository.findByEmail(email));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User update(User entity) {
        return repository.save(entity);
    }

    @Override
    public User create(User entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(User entity) {
        repository.delete(entity);
    }
}
