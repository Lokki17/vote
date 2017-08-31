package com.test.vote.services;

import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import javaslang.control.Option;

import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface VoteCandidateService {

    Option<VoteCandidate> get(Long id);

    List<VoteCandidate> findAll();

    List<VoteCandidate> findAll(VoteTheme theme);

    VoteCandidate update(VoteCandidate entity);

    VoteCandidate create(VoteCandidate entity);

    void delete(VoteCandidate user);

    VoteCandidate addVote(Vote vote);

}
