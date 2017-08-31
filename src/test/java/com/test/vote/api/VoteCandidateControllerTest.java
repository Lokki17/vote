package com.test.vote.api;

import com.test.vote.api.resources.VoteCandidateResource;
import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Random;

import static com.test.vote.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Lokki17
 * @since 30.08.2017
 */
public class VoteCandidateControllerTest extends BaseControllerIntegrationTest {

    private static final String URL = "/candidates";

    private static final String URL_ITEM = URL + "/{id}";

    private static final String URL_VOTE = URL_ITEM + "/votes";

    @Test
    public void getCandidateById() throws Exception {
        VoteCandidate candidate = testContext
                .candidate(VOTE_CANDIDATE, THEME)
                .get();

        mvc.perform(get(URL_ITEM, candidate.getId()))
                .andExpect(jsonPath("$.candidateId").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.votes").isArray())
                .andExpect(jsonPath("$.theme").isNotEmpty())
                .andExpect(status().isOk());

    }

    @Test
    public void getAllCandidates() throws Exception {
        testContext
                .candidate(VOTE_CANDIDATE, THEME)
                .get();

        mvc.perform(get(URL))
                .andExpect(jsonPath("$[0].candidateId").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].votes").isArray())
                .andExpect(jsonPath("$[0].theme").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCandidatesByTheme() throws Exception {
        VoteCandidate candidate = testContext
                .candidate(VOTE_CANDIDATE, THEME)
                .get();

        mvc.perform(get(URL)
        .param("theme", candidate.getTheme().getId().toString()))
                .andExpect(jsonPath("$[0].candidateId").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].votes").isArray())
                .andExpect(jsonPath("$[0].theme").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void createCandidate() throws Exception {
        VoteCandidateResource tmp = SerializationUtils.clone(VOTE_CANDIDATE_RESOURCE);

        VoteTheme theme = testContext
                .theme(THEME)
                .get();

        tmp.setTheme(theme.getId());

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(jsonPath("$.candidateId").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.votes").isArray())
                .andExpect(jsonPath("$.theme").isNotEmpty())
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(status().isCreated());

    }

    @Test
    public void createWithWrongCandidate() throws Exception {
        VoteCandidateResource tmp = SerializationUtils.clone(VOTE_CANDIDATE_RESOURCE);

        testContext
                .theme(THEME)
                .get();

        tmp.setTheme(new Random().nextLong());

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void createWithCandidateIsNull() throws Exception {
        VoteCandidateResource tmp = SerializationUtils.clone(VOTE_CANDIDATE_RESOURCE);

        testContext
                .theme(THEME)
                .get();

        tmp.setTheme(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateCandidate() throws Exception {
        VoteCandidateResource tmp = SerializationUtils.clone(VOTE_CANDIDATE_RESOURCE);
        VoteTheme theme = SerializationUtils.clone(THEME);
        theme.setStartVote(LocalDateTime.now().plusDays(1));
        theme.setFinishVote(LocalDateTime.now().plusDays(2));

        VoteCandidate candidate = testContext
                .candidate(VOTE_CANDIDATE, theme)
                .get();

        tmp.setName("New name");
        tmp.setCandidateId(candidate.getId());
        tmp.setTheme(candidate.getTheme().getId());

        mvc.perform(put(URL_ITEM, candidate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(jsonPath("$.candidateId").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.votes").isArray())
                .andExpect(jsonPath("$.theme").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void updateCandidateAfterVoteBegun() throws Exception {
        VoteCandidateResource tmp = SerializationUtils.clone(VOTE_CANDIDATE_RESOURCE);
        VoteTheme theme = SerializationUtils.clone(THEME);
        theme.setStartVote(LocalDateTime.now().minusDays(1));
        theme.setFinishVote(LocalDateTime.now().plusDays(2));

        VoteCandidate candidate = testContext
                .candidate(VOTE_CANDIDATE, theme)
                .get();

        tmp.setName("New name");
        tmp.setCandidateId(candidate.getId());
        tmp.setTheme(candidate.getTheme().getId());

        mvc.perform(put(URL_ITEM, candidate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteCandidate() throws Exception {
        VoteCandidate theme = testContext
                .candidate(VOTE_CANDIDATE, THEME)
                .get();

        mvc.perform(delete(URL_ITEM, theme.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCandidateWithVotes() throws Exception {
        VoteTheme theme = SerializationUtils.clone(THEME);
        theme.setStartVote(LocalDateTime.now());
        theme.setFinishVote(LocalDateTime.now().plusDays(1));

        Vote vote = testContext
                .vote(VOTE_CANDIDATE, theme, USER)
                .get();

        mvc.perform(delete(URL_ITEM, vote.getCandidate().getId()))
                .andExpect(status().isConflict());
    }

    @Test
    public void createVote() throws Exception {
        VoteTheme theme = SerializationUtils.clone(THEME);
        theme.setStartVote(LocalDateTime.now());
        theme.setFinishVote(LocalDateTime.now().plusDays(1));

        VoteCandidate candidate = testContext
                .currentUser(USER)
                .and()
                .candidate(VOTE_CANDIDATE, theme)
                .get();

        mvc.perform(post(URL_VOTE, candidate.getId()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.candidate").isNotEmpty())
                .andExpect(status().isCreated());
    }

    @Test
    public void createDoubleVote() throws Exception {
        VoteTheme theme = SerializationUtils.clone(THEME);
        theme.setStartVote(LocalDateTime.now());
        theme.setFinishVote(LocalDateTime.now().plusDays(1));

        VoteCandidate candidate = testContext
                .currentUser(USER)
                .and()
                .candidate(VOTE_CANDIDATE, theme)
                .get();

        mvc.perform(post(URL_VOTE, candidate.getId()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.candidate").isNotEmpty())
                .andExpect(status().isCreated());

        mvc.perform(post(URL_VOTE, candidate.getId()))
                .andExpect(status().isConflict());
    }

    @Test
    public void getVotes() throws Exception {
        VoteTheme theme = SerializationUtils.clone(THEME);
        theme.setStartVote(LocalDateTime.now());
        theme.setFinishVote(LocalDateTime.now().plusDays(1));

        Vote vote = testContext
                .vote(VOTE_CANDIDATE, theme, USER)
                .get();

        mvc.perform(get(URL_VOTE, vote.getCandidate().getId()))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].candidate").isNotEmpty())
                .andExpect(status().isOk());
    }

}