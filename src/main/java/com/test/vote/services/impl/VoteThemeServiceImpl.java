package com.test.vote.services.impl;

import com.test.vote.repository.VoteThemeRepository;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteThemeService;
import com.test.vote.services.exception.EntityExistsException;
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
public class VoteThemeServiceImpl implements VoteThemeService {

    @NonNull
    private VoteThemeRepository repository;

    @Override
    public Option<VoteTheme> get(Long id) {
        return Option.of(repository.findOne(id));
    }

    @Override
    public List<VoteTheme> findAll() {
        return repository.findAll();
    }

    @Override
    public VoteTheme update(VoteTheme entity) {
        if (isVoteExists(entity)) {
            throw new EntityExistsException("You can't update VoteTheme. Somebody already have voted in it");
        }

        return repository.save(entity);
    }

    @Override
    public VoteTheme create(VoteTheme entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(VoteTheme entity) {
        if (isVoteExists(entity)) {
            throw new EntityExistsException("You can't delete VoteTheme. Somebody already have voted in it");
        }

        repository.delete(entity);
    }

    private boolean isVoteExists(VoteTheme entity) {
        return entity.getVoteCandidates().stream()
                .anyMatch(candidate -> candidate.getVotes().size() != 0);
    }
}
