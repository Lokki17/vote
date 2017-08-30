package com.test.vote.repository;

import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface VoteCandidateRepository extends JpaRepository<VoteCandidate, Long> {

    List<VoteCandidate> findByTheme(VoteTheme theme);
}
