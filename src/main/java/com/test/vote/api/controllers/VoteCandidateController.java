package com.test.vote.api.controllers;

import com.test.vote.api.mappers.VoteCandidateMapper;
import com.test.vote.api.mappers.VoteMapper;
import com.test.vote.api.resources.VoteCandidateResource;
import com.test.vote.api.resources.VoteResource;
import com.test.vote.api.useragent.UserAgent;
import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteCandidateService;
import com.test.vote.services.VoteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
    private final VoteService voteService;

    @NonNull
    private final VoteCandidateMapper mapper;

    @NonNull
    private final VoteMapper voteMapper;

    @NonNull
    private final UserAgent userAgent;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public VoteCandidateResource getById(@PathVariable("id") VoteCandidate entity) {
        return mapper.toResource(entity);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<VoteCandidateResource> getAll(@RequestParam(name = "theme", required = false) VoteTheme theme) {

        return mapper.toResource(
                theme == null ? service.findAll() : service.findAll(theme)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<VoteCandidateResource> create(@Valid @RequestBody VoteCandidateResource resource) {
        VoteCandidate entity = mapper.fromResource(resource, new VoteCandidate());
        VoteCandidateResource created = mapper.toResource(service.create(entity));
        created.add(linkTo(VoteCandidateController.class).slash(created.getCandidateId()).slash("votes").withSelfRel());

        return new ResponseEntity<>(
                created,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public VoteCandidateResource update(@Valid @RequestBody VoteCandidateResource resource, @PathVariable("id") VoteCandidate entity) {
        VoteCandidate updated = mapper.fromResource(resource, entity);

        return mapper.toResource(service.update(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity delete(@PathVariable("id") VoteCandidate entity) {
        service.delete(entity);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/votes")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<VoteResource> create(@PathVariable("id") VoteCandidate entity) {
        return userAgent.currentUser()
                .map(user -> voteService.create(new Vote(user, entity)))
                .map(vote -> new ResponseEntity<>(voteMapper.toResource(vote), HttpStatus.CREATED))
                .getOrElse(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}/votes")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<VoteResource> getAll(@PathVariable("id") VoteCandidate entity) {

        return voteMapper.toResource(voteService.findAll(entity));
    }
}
