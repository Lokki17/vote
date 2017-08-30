package com.test.vote.repository;

import com.test.vote.repository.entity.VoteCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface VoteCandidateRepository extends JpaRepository<VoteCandidate, Long> {
}
