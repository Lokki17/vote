package com.test.vote;

import com.test.vote.repository.UserRepository;
import com.test.vote.repository.VoteCandidateRepository;
import com.test.vote.repository.VoteRepository;
import com.test.vote.repository.VoteThemeRepository;
import com.test.vote.repository.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Lokki17
 * @since 30.08.2017
 */
public class TestDataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteThemeRepository voteThemeRepository;

    @Autowired
    private VoteCandidateRepository voteCandidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private TransactionTemplate tx;

    @PostConstruct
    public void initialize() {
        tx.execute(transactionStatus -> {

            User user1 = createUserIfNotPresent(
                    User.builder()
                            .name("User 1")
                            .email("email@mail.ru")
                            .password("secret")
                            .authority(Authority.ROLE_ADMIN)
                            .build()
            );

            User user2 = createUserIfNotPresent(
                    User.builder()
                            .name("User 2")
                            .email("email@gmail.com")
                            .password("secret")
                            .authority(Authority.ROLE_USER)
                            .build()
            );

            User user3 = createUserIfNotPresent(
                    User.builder()
                            .name("User 3")
                            .email("anyEmail@mail.com")
                            .password("secret")
                            .authority(Authority.ROLE_USER)
                            .build()
            );

            VoteTheme theme1 = createVoteThemeIfNotPresent(
                    VoteTheme.builder()
                            .name("Theme 1")
                            .startVote(LocalDateTime.now())
                            .finishVote(LocalDateTime.now().plusDays(1))
                            .build()
            );

            VoteTheme theme2 = createVoteThemeIfNotPresent(
                    VoteTheme.builder()
                            .name("Theme 2")
                            .startVote(LocalDateTime.now())
                            .finishVote(LocalDateTime.now().plusDays(1))
                            .build()
            );

            VoteCandidate candidate11 = createVoteCandidateIfNotPresent(
                    VoteCandidate.builder()
                            .name("Candidate 1 theme 1")
                            .theme(theme1)
                            .votes(new ArrayList<>())
                            .build()
            );

            VoteCandidate candidate21 = createVoteCandidateIfNotPresent(
                    VoteCandidate.builder()
                            .name("Candidate 2 theme 1")
                            .theme(theme1)
                            .votes(new ArrayList<>())
                            .build()
            );

            VoteCandidate candidate12 = createVoteCandidateIfNotPresent(
                    VoteCandidate.builder()
                            .name("Candidate 1 theme 2")
                            .theme(theme2)
                            .votes(new ArrayList<>())
                            .build()
            );

            VoteCandidate candidate22 = createVoteCandidateIfNotPresent(
                    VoteCandidate.builder()
                            .name("Candidate 2 theme 2")
                            .theme(theme2)
                            .votes(new ArrayList<>())
                            .build()
            );

            theme1.setVoteCandidates(new HashSet<>(Arrays.asList(candidate11, candidate21)));
            theme2.setVoteCandidates(new HashSet<>(Arrays.asList(candidate12, candidate22)));
            voteThemeRepository.save(theme1);
            voteThemeRepository.save(theme2);

            Vote vote1 = createVoteIfNotPresent(
                    new Vote(user1, candidate11)
            );

            Vote vote2 = createVoteIfNotPresent(
                    new Vote(user2, candidate12)
            );

            Vote vote3 = createVoteIfNotPresent(
                    new Vote(user1, candidate21)
            );

            Vote vote4 = createVoteIfNotPresent(
                    new Vote(user3, candidate22)
            );

            candidate11.getVotes().add(vote1);
            voteCandidateRepository.save(candidate11);
            candidate12.getVotes().add(vote2);
            voteCandidateRepository.save(candidate12);
            candidate21.getVotes().add(vote3);
            voteCandidateRepository.save(candidate21);
            candidate22.getVotes().add(vote4);
            voteCandidateRepository.save(candidate22);

            return null;
        });

    }

    private User createUserIfNotPresent(User user) {
        User result = userRepository.findByEmail(user.getEmail());
        if (result != null) {
            return result;
        }

        return userRepository.saveAndFlush(user);
    }

    private VoteTheme createVoteThemeIfNotPresent(VoteTheme entity) {
        List<VoteTheme> result = voteThemeRepository.findAll();

        return result.stream()
                .filter(theme -> theme.getName().equals(entity.getName()))
                .findAny()
                .orElseGet(() -> voteThemeRepository.saveAndFlush(entity));
    }

    private VoteCandidate createVoteCandidateIfNotPresent(VoteCandidate entity) {
        List<VoteCandidate> result = voteCandidateRepository.findAll();

        return result.stream()
                .filter(theme -> theme.getName().equals(entity.getName()))
                .findAny()
                .orElseGet(() -> voteCandidateRepository.saveAndFlush(entity));
    }

    private Vote createVoteIfNotPresent(Vote entity) {
        List<Vote> result = voteRepository.findAll();
        return result.stream()
                .filter(vote -> vote.equals(entity))
                .findAny()
                .orElseGet(() -> voteRepository.saveAndFlush(entity));
    }


}
