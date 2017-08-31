package com.test.vote.services.impl;

import com.test.vote.repository.VoteCandidateRepository;
import com.test.vote.repository.VoteRepository;
import com.test.vote.repository.VoteThemeRepository;
import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteCandidateService;
import com.test.vote.services.VoteThemeService;
import com.test.vote.services.exception.EntityExistsException;
import com.test.vote.services.exception.EntityNotFoundException;
import com.test.vote.services.exception.IllegalTimeException;
import javaslang.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @NonNull
    private VoteRepository voteRepository;

    @NonNull
    private VoteThemeService themeService;

    @Override
    public Option<VoteCandidate> get(Long id) {
        return Option.of(repository.findOne(id));
    }

    @Override
    public List<VoteCandidate> findAll() {
        return repository.findAll();
    }

    @Override
    public List<VoteCandidate> findAll(VoteTheme theme) {
        return repository.findByTheme(theme);
    }

    @Override
    public VoteCandidate update(VoteCandidate entity) {
        if (LocalDateTime.now().isAfter(entity.getTheme().getStartVote())) {
            throw new IllegalTimeException("Voting is begun");
        }

        return repository.save(entity);
    }

    @Override
    public VoteCandidate create(VoteCandidate entity) {
        VoteCandidate created = repository.save(entity);
        themeService.addCandidate(created);
        return created;
    }

    @Override
    public void delete(VoteCandidate entity) {
        if (!voteRepository.existsByCandidate(entity)) {

            repository.delete(entity);
        } else throw new EntityExistsException("Candidate has some votes and cant be deleted");
    }

    @Override
    public VoteCandidate addVote(Vote vote) {
        return get(vote.getCandidate().getId())
                .map(candidate -> {
                    candidate.getVotes().add(vote);
                    return repository.save(candidate);
                })
                .getOrElseThrow(() -> new EntityNotFoundException("Candidate with id" + vote.getCandidate().getId() + " not found"));
    }
}
