package com.test.vote.api.controllers;

import com.test.vote.api.mappers.VoteCandidateMapper;
import com.test.vote.api.resources.VoteCandidateResource;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.services.VoteCandidateService;
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
@RequestMapping("/candidates")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteCandidateController {

    @NonNull
    private final VoteCandidateService service;

    @NonNull
    private final VoteCandidateMapper mapper;

    @GetMapping("/{id}")
    public VoteCandidateResource getById(@PathVariable("id") VoteCandidate entity) {
        return mapper.toResource(entity);
    }

    @GetMapping
    public List<VoteCandidateResource> getAll() {
        return service.findAll().stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<VoteCandidateResource> create(@Valid @RequestBody VoteCandidateResource resource) {
        VoteCandidate entity = mapper.fromResource(resource, new VoteCandidate());

        return new ResponseEntity<>(
                mapper.toResource(service.create(entity)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public VoteCandidateResource update(@Valid @RequestBody VoteCandidateResource resource, @PathVariable("id") VoteCandidate entity) {
        VoteCandidate updated = mapper.fromResource(resource, entity);

        return mapper.toResource(service.update(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") VoteCandidate entity) {
        service.delete(entity);

        return ResponseEntity.noContent().build();
    }
}
