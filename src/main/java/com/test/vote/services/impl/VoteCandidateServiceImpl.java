package com.test.vote.services.impl;

import com.test.vote.repository.VoteCandidateRepository;
import com.test.vote.repository.VoteRepository;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.services.VoteCandidateService;
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
public class VoteCandidateServiceImpl implements VoteCandidateService {

    @NonNull
    private VoteCandidateRepository repository;

    @Override
    public Option<VoteCandidate> get(Long id) {
        return Option.of(repository.findOne(id));
    }

    @Override
    public List<VoteCandidate> findAll() {
        return repository.findAll();
    }

    @Override
    public VoteCandidate update(VoteCandidate entity) {
        return repository.save(entity);
    }

    @Override
    public VoteCandidate create(VoteCandidate entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(VoteCandidate entity) {
        repository.delete(entity);
    }
}
