package com.test.vote;

import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.api.resources.VoteCandidateResource;
import com.test.vote.repository.entity.VoteTheme;

import java.time.LocalDateTime;

/**
 * @author Lokki17
 * @since 30.08.2017
 */
public class TestData {

    public static VoteTheme THEME = VoteTheme.builder()
            .name("Theme 1")
            .startVote(LocalDateTime.now())
            .finishVote(LocalDateTime.now().plusDays(1))
            .build();

    public static VoteThemeResource THEME_RESOURCE = VoteThemeResource.builder()
            .name("Theme 1")
            .startVote(LocalDateTime.now())
            .finishVote(LocalDateTime.now().plusDays(1))
            .build();

    public static VoteCandidateResource VOTE_CANDIDATE_RESOURCE = VoteCandidateResource.builder()
            .name("")
            .theme(1L)
            .build();
}
