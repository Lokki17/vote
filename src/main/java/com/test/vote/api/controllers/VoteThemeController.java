package com.test.vote.api.controllers;

import com.test.vote.api.mappers.VoteThemeMapper;
import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteThemeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteThemeController {

    @NonNull
    private final VoteThemeService service;

    @NonNull
    private final VoteThemeMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public VoteThemeResource getById(@PathVariable("id") VoteTheme entity) {
        return mapper.toResource(entity);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<VoteThemeResource> getAll() {
        return service.findAll().stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<VoteThemeResource> create(@Valid @RequestBody VoteThemeResource resource) {
        VoteTheme entity = mapper.fromResource(resource, new VoteTheme());
        VoteThemeResource created = mapper.toResource(service.create(entity));
        created.add(linkTo(VoteThemeController.class).slash(created.getThemeId()).withSelfRel());

        return new ResponseEntity<>(
                created,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public VoteThemeResource update(@Valid @RequestBody VoteThemeResource resource, @PathVariable("id") VoteTheme entity) {
        VoteTheme updated = mapper.fromResource(resource, entity);

        return mapper.toResource(service.update(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity delete(@PathVariable("id") VoteTheme entity) {
        service.delete(entity);

        return ResponseEntity.noContent().build();
    }
}
