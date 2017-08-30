package com.test.vote.api;

import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.repository.entity.VoteTheme;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.test.vote.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
                .theme(THEME)
                .get();

        mvc.perform(get(URL_ITEM, theme.getId()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.startVote").isNotEmpty())
                .andExpect(jsonPath("$.finishVote").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void getAll() throws Exception {
        testContext
                .theme(THEME)
                .get();

        mvc.perform(get(URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].startVote").isNotEmpty())
                .andExpect(jsonPath("$[0].finishVote").isNotEmpty())
                .andExpect(jsonPath("$[0].voteCandidates").isArray())
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(THEME_RESOURCE)))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.startVote").isNotEmpty())
                .andExpect(jsonPath("$.finishVote").isNotEmpty())
                .andExpect(jsonPath("$.voteCandidates").isArray())
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        VoteThemeResource tmp = SerializationUtils.clone(THEME_RESOURCE);
        tmp.setName("New theme");

        VoteTheme theme = testContext
                .theme(THEME)
                .get();

        mvc.perform(put(URL_ITEM, theme.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.startVote").isNotEmpty())
                .andExpect(jsonPath("$.finishVote").isNotEmpty())
                .andExpect(jsonPath("$.voteCandidates").isArray())
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {
        VoteTheme theme = testContext
                .theme(THEME)
                .get();

        mvc.perform(MockMvcRequestBuilders.delete(URL_ITEM, theme.getId()))
                .andExpect(status().isNoContent());
    }

}