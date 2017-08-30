package com.test.vote.api.controllers;

import com.test.vote.api.mappers.VoteCandidateMapper;
import com.test.vote.api.mappers.VoteMapper;
import com.test.vote.api.mappers.VoteThemeMapper;
import com.test.vote.api.resources.VoteCandidateResource;
import com.test.vote.api.resources.VoteResource;
import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteCandidateService;
import com.test.vote.services.VoteService;
import com.test.vote.services.VoteThemeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@RestController
@RequestMapping("/")
//@RequestMapping("/candidates")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteCandidateController {

    @NonNull
    private final VoteCandidateService service;

    @NonNull
    private final VoteService voteService;

    @NonNull
    private final VoteCandidateMapper mapper;

    @NonNull
    private final VoteMapper voteMapper;

    @NonNull
    private final VoteThemeService voteThemeService;

    @NonNull
    private final VoteThemeMapper voteThemeMapper;

    @GetMapping("/{id}")
    public VoteCandidateResource getById(@PathVariable("id") VoteCandidate entity) {
        return mapper.toResource(entity);
    }

    @GetMapping
    public List<VoteCandidateResource> getAll(@RequestParam(name = "theme", required = false) VoteTheme theme) {

        return mapper.toResource(
                theme == null ? service.findAll() : service.findAll(theme)
        );
    }

    @PostMapping
    public ResponseEntity<VoteCandidateResource> create(@RequestBody VoteCandidateResource resource) {
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

    @PostMapping("/{id}/votes")
    public ResponseEntity<VoteResource> create(@PathVariable("id") VoteCandidate entity) {
        Vote vote = new Vote(null, entity);

        return new ResponseEntity<>(
                voteMapper.toResource(voteService.create(vote)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}/votes")
    public List<VoteResource> getAll(@PathVariable("id") VoteCandidate entity) {

        return voteMapper.toResource(voteService.findAll(entity));
    }
}
