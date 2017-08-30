package com.test.vote.api;

import com.google.common.collect.ImmutableList;
import com.test.vote.repository.UserRepository;
import com.test.vote.repository.VoteCandidateRepository;
import com.test.vote.repository.VoteRepository;
import com.test.vote.repository.VoteThemeRepository;
import com.test.vote.repository.entity.*;
import com.test.vote.services.UserService;
import com.test.vote.services.VoteCandidateService;
import com.test.vote.services.VoteService;
import com.test.vote.services.VoteThemeService;
import javaslang.control.Option;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final UserService userService;

    private final UserRepository userRepository;


    public TestVote vote(VoteCandidate data, VoteTheme theme, User user) {

        TestVoteCandidate tmp = candidate(data, theme);
        TestUser temp = currentUser(user);

        return new TestVote(new Vote(temp.get(), tmp.get()));
    }

    public TestVoteCandidate candidate(VoteCandidate data, VoteTheme theme) {
        VoteCandidate tmp = SerializationUtils.clone(data);
        VoteTheme temp = SerializationUtils.clone(theme);

        VoteTheme tmpTheme = new TestVoteTheme(temp).get();
        tmp.setTheme(tmpTheme);

        return new TestVoteCandidate(tmp);
    }

    public TestVoteTheme theme(VoteTheme data) {
        VoteTheme tmp = SerializationUtils.clone(data);

        return new TestVoteTheme(tmp);
    }

    public TestUser currentUser(User user) {
        TestUser currentUser = new TestUser(user, this);
        SecurityContextHolder.getContext().setAuthentication(authOf(currentUser.get()));
        return currentUser;
    }


    public class TestVote {

        final Vote self;

        TestVote(Vote data) {
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

            this.self = Option.of(data.getId())
                    .map(value -> voteCandidateService.get(value)
                            .get())
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

    public class TestUser {

        final User self;

        final TestContext context;

        TestUser(User data, TestContext context) {
            User tmp = SerializationUtils.clone(data);
            this.context = context;
            this.self = Option.of(tmp.getId())
                    .map(value -> userService.get(value)
                            .get())
                    .getOrElse(() -> userService.create(tmp));
        }

        public User get() {
            return self;
        }

        public TestContext and() {
            return context;
        }
    }

    @Override
    protected void after() {
        voteThemeRepository.deleteAll();
    }

    private static Authentication authOf(Object principal, Authority... authorities) {
        TestingAuthenticationToken auth = new TestingAuthenticationToken(principal, null, ImmutableList.copyOf(authorities));
        auth.setAuthenticated(true);
        return auth;
    }

}
