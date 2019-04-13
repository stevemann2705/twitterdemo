package in.stevemann.twitterdemo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class TwitterControllerTest {

    private TwitterController controller;

    MockMvc mock;

    @Mock
    TwitterTemplateCreator templateCreator;

    @Mock
    private TwitterService service;

    @Mock
    private Twitter twitter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new TwitterController(service, templateCreator);
        mock = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getHomePage() throws Exception {
        mock.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void makeTweetWithoutExceptions() {
        service.tweet(twitter, " ");
    }

    @Test
    public void getTweet() {
        Tweet tweet = new Tweet(1L, "ss", Date.from(Instant.now()), " ", " ", 1L, 2L, " ", " ");
        when(service.getTweet(twitter, "1")).thenReturn(tweet);

        Tweet tweet1 = service.getTweet(twitter, "1");
        assertNotNull(tweet1);
    }
}