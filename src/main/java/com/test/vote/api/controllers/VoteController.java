package com.test.vote.api.controllers;

import com.test.vote.api.mappers.VoteMapper;
import com.test.vote.api.resources.VoteResource;
import com.test.vote.repository.entity.Vote;
import com.test.vote.services.VoteService;
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
@RequestMapping("/votes")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteController {

    @NonNull
    private final VoteService service;

    @NonNull
    private final VoteMapper mapper;

    @GetMapping("/{id}")
    public VoteResource getById(@PathVariable("id") Vote entity) {
        return mapper.toResource(entity);
    }

    @GetMapping
    public List<VoteResource> getAll() {
        return service.findAll().stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<VoteResource> create(@Valid @RequestBody VoteResource resource) {
        Vote entity = mapper.fromResource(resource, new Vote(), null);

        return new ResponseEntity<>(
                mapper.toResource(service.create(entity)),
                HttpStatus.CREATED
        );
    }
}
