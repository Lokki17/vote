package com.test.vote.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.vote.VoteApplication;
import com.test.vote.api.config.Config;
import com.test.vote.repository.UserRepository;
import com.test.vote.repository.VoteCandidateRepository;
import com.test.vote.repository.VoteRepository;
import com.test.vote.repository.VoteThemeRepository;
import javaslang.control.Try;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {VoteApplication.class},
        properties = "spring.config.name=application,dao"
)
@ActiveProfiles("test")
public abstract class BaseControllerIntegrationTest {

    public static final MediaType APP_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteCandidateRepository voteCandidateRepository;

    @Autowired
    private VoteThemeRepository voteThemeRepository;

    @Autowired
    private UserRepository userRepository;

    protected MockMvc mvc;

    @Autowired
    public TestContext testContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    protected String json(Object object) {
        return Try.of(() -> objectMapper.writeValueAsString(object))
                .getOrElseThrow((Throwable e) -> new RuntimeException(e));
    }

    private ResultHandler print() {
        return result -> {
            System.out.println("Request body:");
            ByteStreams.copy(result.getRequest().getInputStream(), System.out);

            System.out.println("\n\nResponse:");
            System.out.println("Status: " + result.getResponse().getStatus());
            String content = result.getResponse().getContentAsString();
            if (content.length() > 0 && result.getResponse().getContentType().contains("json")) {
                try {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonElement element = new JsonParser().parse(content);
                    System.out.println(gson.toJson(element));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @After
    public void tearDown() {
        voteRepository.deleteAll();
        voteCandidateRepository.deleteAll();
        userRepository.deleteAll();
        voteThemeRepository.deleteAll();
    }
}
