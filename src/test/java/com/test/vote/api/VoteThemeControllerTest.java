package com.test.vote.api;

import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.repository.entity.Authority;
import com.test.vote.repository.entity.VoteTheme;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Random;

import static com.test.vote.TestData.*;
import static com.test.vote.repository.entity.Authority.ROLE_ADMIN;
import static com.test.vote.repository.entity.Authority.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/**
 * @author Lokki17
 * @since 30.08.2017
 */
public class VoteThemeControllerTest extends BaseControllerIntegrationTest{

    private static final String URL = "/themes";

    private static final String URL_ITEM = URL + "/{id}";

    @Test
    public void getById() throws Exception {
        VoteTheme theme = testContext
                .currentUser(USER, ROLE_ADMIN)
                .and()
                .theme(THEME)
                .get();

        mvc.perform(get(URL_ITEM, theme.getId()))
                .andExpect(jsonPath("$.themeId").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.startVote").isNotEmpty())
                .andExpect(jsonPath("$.finishVote").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdWithROLE_USER() throws Exception {
        VoteTheme theme = testContext
                .currentUser(USER, ROLE_USER)
                .and()
                .theme(THEME)
                .get();

        mvc.perform(get(URL_ITEM, theme.getId()))
                .andExpect(jsonPath("$.themeId").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.startVote").isNotEmpty())
                .andExpect(jsonPath("$.finishVote").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void getAll() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .and()
                .theme(THEME)
                .get();

        mvc.perform(get(URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].themeId").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].startVote").isNotEmpty())
                .andExpect(jsonPath("$[0].finishVote").isNotEmpty())
                .andExpect(jsonPath("$[0].voteCandidates").isArray())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllWithROLE_USER() throws Exception {
        testContext
                .currentUser(USER, ROLE_USER)
                .and()
                .theme(THEME)
                .get();

        mvc.perform(get(URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].themeId").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].startVote").isNotEmpty())
                .andExpect(jsonPath("$[0].finishVote").isNotEmpty())
                .andExpect(jsonPath("$[0].voteCandidates").isArray())
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(THEME_RESOURCE)))
                .andExpect(jsonPath("$.themeId").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.startVote").isNotEmpty())
                .andExpect(jsonPath("$.finishVote").isNotEmpty())
                .andExpect(jsonPath("$.voteCandidates").isArray())
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(status().isCreated());
    }

    @Test
    public void createWithROLE_USER() throws Exception {
        testContext
                .currentUser(USER, ROLE_USER);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(THEME_RESOURCE)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createWithIllegalDates() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .and();

        VoteThemeResource tmp = SerializationUtils.clone(THEME_RESOURCE);
        tmp.setStartVote(LocalDateTime.now().plusDays(1));
        tmp.setFinishVote(LocalDateTime.now());

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithWrongStartDate() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .and();

        VoteThemeResource tmp = SerializationUtils.clone(THEME_RESOURCE);
        tmp.setStartVote(LocalDateTime.now().minusDays(1));
        tmp.setFinishVote(LocalDateTime.now().plusDays(1));

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithStartDateIsNull() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .and();

        VoteThemeResource tmp = SerializationUtils.clone(THEME_RESOURCE);
        tmp.setStartVote(null);
        tmp.setFinishVote(LocalDateTime.now().plusDays(1));

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithEndDateIsNull() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .and();

        VoteThemeResource tmp = SerializationUtils.clone(THEME_RESOURCE);
        tmp.setStartVote(LocalDateTime.now().plusDays(1));
        tmp.setFinishVote(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .and();

        VoteThemeResource tmp = SerializationUtils.clone(THEME_RESOURCE);
        tmp.setName("New theme");

        VoteTheme theme = testContext
                .theme(THEME)
                .get();

        tmp.setThemeId(theme.getId());

        mvc.perform(put(URL_ITEM, theme.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(jsonPath("$.themeId").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.startVote").isNotEmpty())
                .andExpect(jsonPath("$.finishVote").isNotEmpty())
                .andExpect(jsonPath("$.voteCandidates").isArray())
                .andExpect(status().isOk());
    }

    @Test
    public void updateWithROLE_USER() throws Exception {
        testContext
                .currentUser(USER, ROLE_USER)
                .and();

        VoteThemeResource tmp = SerializationUtils.clone(THEME_RESOURCE);
        tmp.setName("New theme");

        VoteTheme theme = testContext
                .theme(THEME)
                .get();

        tmp.setThemeId(theme.getId());

        mvc.perform(put(URL_ITEM, theme.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteEntity() throws Exception {
        VoteTheme theme = testContext
                .currentUser(USER, ROLE_ADMIN)
                .and()
                .theme(THEME)
                .get();

        mvc.perform(delete(URL_ITEM, theme.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteEntityWithROLE_USER() throws Exception {
        VoteTheme theme = testContext
                .currentUser(USER, ROLE_USER)
                .and()
                .theme(THEME)
                .get();

        mvc.perform(delete(URL_ITEM, theme.getId()))
                .andExpect(status().isForbidden());
    }
}