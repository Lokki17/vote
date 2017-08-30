package com.test.vote.api;

import com.test.vote.repository.VoteCandidateRepository;
import com.test.vote.repository.VoteRepository;
import com.test.vote.repository.VoteThemeRepository;
import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteCandidateService;
import com.test.vote.services.VoteService;
import com.test.vote.services.VoteThemeService;
import javaslang.control.Option;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TestContext extends ExternalResource {

    private final VoteService voteService;

    private final VoteRepository voteRepository;

    private final VoteCandidateService voteCandidateService;

    private final VoteCandidateRepository voteCandidateRepository;

    private final VoteThemeService voteThemeService;

    private final VoteThemeRepository voteThemeRepository;


    public TestVote vote(Vote data) {
        Vote tmp = SerializationUtils.clone(data);

        return new TestVote(tmp);
    }

    public TestVoteCandidate candidate(VoteCandidate data) {
        VoteCandidate tmp = SerializationUtils.clone(data);

        return new TestVoteCandidate(tmp);
    }

    public TestVoteTheme theme(VoteTheme data) {
        VoteTheme tmp = SerializationUtils.clone(data);

        return new TestVoteTheme(tmp);
    }


    public class TestVote {

        final Vote self;

        TestVote(Vote data) {
            new TestVote(data);

            this.self = Option.of(data.getId())
                    .map(value -> voteService.get(value)
                            .getOrElse(() -> voteService.create(data)))
                    .getOrElse(() -> voteService.create(data));
        }

        public Vote get() {
            return self;
        }
    }

    public class TestVoteCandidate {

        final VoteCandidate self;

        TestVoteCandidate(VoteCandidate data) {

            this.self = voteCandidateService.get(data.getId())
                    .getOrElse(() -> voteCandidateService.create(data));
        }

        public VoteCandidate get() {
            return self;
        }
    }

    public class TestVoteTheme {

        final VoteTheme self;

        TestVoteTheme(VoteTheme data) {

            this.self = Option.of(data.getId())
                    .map(value -> voteThemeService.get(value)
                            .get())
                    .getOrElse(() -> voteThemeService.create(data));
        }

        public VoteTheme get() {
            return self;
        }
    }

    @Override
    protected void after() {
        voteThemeRepository.deleteAll();
    }

}
