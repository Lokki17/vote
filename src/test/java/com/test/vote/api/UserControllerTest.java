package com.test.vote.api;

import com.test.vote.api.resources.UserResource;
import com.test.vote.repository.entity.User;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.test.vote.TestData.NEW_USER_RESOURCE;
import static com.test.vote.TestData.USER;
import static com.test.vote.repository.entity.Authority.ROLE_ADMIN;
import static com.test.vote.repository.entity.Authority.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Lokki17
 * @since 30.08.2017
 */
public class UserControllerTest extends BaseControllerIntegrationTest {

    private static final String URL = "/users";

    private static final String URL_ITEM = URL + "/{id}";

    @Test
    public void getById() throws Exception {
        User user = testContext
                .currentUser(USER, ROLE_ADMIN)
                .get();

        mvc.perform(get(URL_ITEM, user.getId()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdWithROLE_USER() throws Exception {
        User user = testContext
                .currentUser(USER, ROLE_USER)
                .get();

        mvc.perform(get(URL_ITEM, user.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAll() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .get();

        mvc.perform(get(URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].email").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllWithROLE_USER() throws Exception {
        testContext
                .currentUser(USER, ROLE_USER)
                .get();

        mvc.perform(get(URL))
                .andExpect(status().isForbidden());
    }

    @Test
    public void create() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(NEW_USER_RESOURCE)))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(status().isCreated());
    }

    @Test
    public void createWithROLE_USER() throws Exception {
        testContext
                .currentUser(USER, ROLE_USER);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(NEW_USER_RESOURCE)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createWithEmailIsNull() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN);

        UserResource tmp = SerializationUtils.clone(NEW_USER_RESOURCE);
        tmp.setEmail(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithNamesNull() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN);

        UserResource tmp = SerializationUtils.clone(NEW_USER_RESOURCE);
        tmp.setName(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithPasswordNull() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN);

        UserResource tmp = SerializationUtils.clone(NEW_USER_RESOURCE);
        tmp.setPassword(null);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithDoubleEmail() throws Exception {
        testContext
                .currentUser(USER, ROLE_ADMIN)
                .get();

        UserResource tmp = SerializationUtils.clone(NEW_USER_RESOURCE);
        tmp.setEmail(USER.getEmail());

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(tmp)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        UserResource tmp = SerializationUtils.clone(NEW_USER_RESOURCE);
        tmp.setName("New name");

        User user = testContext
                .currentUser(USER, ROLE_ADMIN)
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
                .currentUser(USER, ROLE_ADMIN)
                .get();

        mvc.perform(delete(URL_ITEM, user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteWithROLE_USER() throws Exception {
        User user = testContext
                .currentUser(USER, ROLE_USER)
                .get();

        mvc.perform(delete(URL_ITEM, user.getId()))
                .andExpect(status().isForbidden());
    }
}