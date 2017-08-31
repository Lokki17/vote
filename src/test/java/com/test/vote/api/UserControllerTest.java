package com.test.vote.api;

import com.test.vote.api.resources.UserResource;
import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.repository.entity.User;
import com.test.vote.repository.entity.VoteTheme;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static com.test.vote.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Lokki17
 * @since 30.08.2017
 */
public class UserControllerTest extends BaseControllerIntegrationTest{

    private static final String URL = "/users";

    private static final String URL_ITEM = URL + "/{id}";

    @Test
    public void getById() throws Exception {
        User user = testContext
                .currentUser(USER)
                .get();

        mvc.perform(get(URL_ITEM, user.getId()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void getAll() throws Exception {
        testContext
                .currentUser(USER)
                .get();

        mvc.perform(get(URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].email").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(USER_RESOURCE)))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(status().isCreated());
    }

    @Test
    public void createWithEmailIsNull() throws Exception {
        UserResource tmp = SerializationUtils.clone(USER_RESOURCE);
        tmp.setEmail(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithNamesNull() throws Exception {
        UserResource tmp = SerializationUtils.clone(USER_RESOURCE);
        tmp.setName(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithPasswordNull() throws Exception {
        UserResource tmp = SerializationUtils.clone(USER_RESOURCE);
        tmp.setPassword(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithDoubleEmail() throws Exception {
        testContext
                .currentUser(USER)
                .get();

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(USER_RESOURCE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        UserResource tmp = SerializationUtils.clone(USER_RESOURCE);
        tmp.setName("New name");

        User user = testContext
                .currentUser(USER)
                .get();

        tmp.setId(user.getId());

        mvc.perform(put(URL_ITEM, user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEntity() throws Exception {
        User user = testContext
                .currentUser(USER)
                .get();

        mvc.perform(delete(URL_ITEM, user.getId()))
                .andExpect(status().isNoContent());
    }
}