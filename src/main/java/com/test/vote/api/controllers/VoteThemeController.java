package com.test.vote.api.controllers;

import com.test.vote.api.mappers.VoteThemeMapper;
import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteThemeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public VoteThemeResource getById(@PathVariable("id") VoteTheme entity) {
        return mapper.toResource(entity);
    }

    @GetMapping
    public List<VoteThemeResource> getAll() {
        return service.findAll().stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<VoteThemeResource> create(@Valid @RequestBody VoteThemeResource resource) {
        VoteTheme entity = mapper.fromResource(resource, new VoteTheme());

        return new ResponseEntity<>(
                mapper.toResource(service.create(entity)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public VoteThemeResource update(@Valid @RequestBody VoteThemeResource resource, @PathVariable("id") VoteTheme entity) {
        VoteTheme updated = mapper.fromResource(resource, entity);

        return mapper.toResource(service.update(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") VoteTheme entity) {
        service.delete(entity);

        return ResponseEntity.noContent().build();
    }
}
