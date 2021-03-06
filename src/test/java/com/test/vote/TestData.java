package com.test.vote;

import com.test.vote.api.resources.UserResource;
import com.test.vote.api.resources.VoteResource;
import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.api.resources.VoteCandidateResource;
import com.test.vote.repository.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Lokki17
 * @since 30.08.2017
 */
public class TestData {

    public static User USER = User.builder()
            .name("User 1")
            .email("email@mail.ru")
            .password("secret")
            .authority(Authority.ROLE_ADMIN)
            .build();

    public static VoteTheme THEME = VoteTheme.builder()
            .name("Theme 1")
            .startVote(LocalDateTime.now().plusDays(1))
            .finishVote(LocalDateTime.now().plusDays(2))
            .build();

    public static VoteCandidate VOTE_CANDIDATE = VoteCandidate.builder()
            .name("Candidate 1")
            .theme(THEME)
            .votes(new ArrayList<>())
            .build();

    public static Vote VOTE = Vote.builder()
            .user(USER)
            .candidate(VOTE_CANDIDATE)
            .build();

    public static VoteThemeResource THEME_RESOURCE = VoteThemeResource.builder()
            .name("Theme 1")
            .startVote(THEME.getStartVote())
            .finishVote(THEME.getFinishVote())
            .build();

    public static UserResource NEW_USER_RESOURCE = UserResource.builder()
            .email("some@email.ru")
            .name("New user")
            .password("secret")
            .authority(Authority.ROLE_USER)
            .build();

    public static VoteCandidateResource VOTE_CANDIDATE_RESOURCE = VoteCandidateResource.builder()
            .name(VOTE_CANDIDATE.getName())
            .theme(THEME.getId())
            .votes(new ArrayList<>())
            .build();

    public static VoteResource VOTE_RESOURCE = VoteResource.builder()
            .candidate(VOTE_CANDIDATE.getId())
            .build();
}
