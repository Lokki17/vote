package com.test.vote.services.impl;

import com.test.vote.repository.VoteRepository;
import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteService;
import com.test.vote.services.exception.EntityExistsException;
import javaslang.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteServiceImpl implements VoteService {

    @NonNull
    private VoteRepository repository;

    @Override
    public Option<Vote> get(Long id) {
        return Option.of(repository.findOne(id));
    }

    @Override
    public List<Vote> findAll(VoteCandidate candidate) {
        return repository.findAll();
    }

    @Override
    public Vote create(Vote entity) {
        if (repository.existsByUserAndCandidate_Theme(entity.getUser(), entity.getCandidate().getTheme())) {
            throw new EntityExistsException("User with email " + entity.getUser().getEmail() +
                    " already voted");
        }
        checkTime(entity);

        return repository.save(entity);
    }

    private void checkTime(Vote entry) {
        LocalDateTime now = LocalDateTime.now();
        VoteTheme voteTheme = entry.getCandidate().getTheme();
//        if (now.isBefore(voteTheme.getStartVote()) || now.isAfter(voteTheme.getFinishVote())) {
//            throw new IllegalTimeException("Vote time is ended or not started yet");
//        }
    }
}
