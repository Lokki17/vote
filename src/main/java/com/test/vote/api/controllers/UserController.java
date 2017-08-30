package com.test.vote.api.controllers;

import com.test.vote.api.mappers.UserMapper;
import com.test.vote.api.resources.UserResource;
import com.test.vote.repository.entity.User;
import com.test.vote.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserController {

    @NonNull
    private final UserService service;

    @NonNull
    private final UserMapper mapper;

    @GetMapping("/{id}")
    public UserResource getById(@PathVariable("id") User entity) {
        return mapper.toResource(entity);
    }

    @GetMapping
    public List<UserResource> getAll() {
        return service.findAll().stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<UserResource> create(@Valid @RequestBody UserResource resource) {
        User entity = mapper.fromResource(resource, new User());

        return new ResponseEntity<>(
                mapper.toResource(service.create(entity)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public UserResource update(@Valid @RequestBody UserResource resource, @PathVariable("id") User entity) {
        User updated = mapper.fromResource(resource, entity);

        return mapper.toResource(service.update(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") User entity) {
        service.delete(entity);

        return ResponseEntity.noContent().build();
    }
}
