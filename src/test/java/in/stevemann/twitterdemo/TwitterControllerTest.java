package in.stevemann.twitterdemo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TwitterControllerTest {

    private TwitterController controller;

    MockMvc mock;

    @Mock
    TwitterTemplateCreator templateCreator;

    @Mock
    private TwitterService service;

    private Twitter twitter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new TwitterController(service, templateCreator);
        mock = MockMvcBuilders.standaloneSetup(controller).build();
        twitter = templateCreator.getTwitterTemplate("stevemann2705");
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
    public void getTweet() throws Exception {
        TwitterProfile user = new TwitterProfile(1L, " ", "UserName", " ", " ", " ", " ", Date.from(Instant.now()));
        Tweet tweet = new Tweet(1L, "ss", Date.from(Instant.now()), " ", " ", 1L, 2L, " ", " ");
        tweet.setUser(user);
        tweet.setRetweetCount(2);
        tweet.setFavoriteCount(3);
        when(service.getTweet(twitter, "1")).thenReturn(tweet);

        mock.perform(get("/gettweet/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tweetText", "tweetUser", "tweetRetweetCount", "tweetFavoriteCount"))
                .andExpect(view().name("showtweet"));

        Tweet tweet1 = service.getTweet(twitter, "1");
        assertNotNull(tweet1);
    }

    @Test
    public void getHomeTimeline() throws Exception {
        Tweet tweet1 = new Tweet(1L, "ss", Date.from(Instant.now()), " ", " ", 1L, 2L, " ", " ");
        Tweet tweet2 = new Tweet(2L, "ssss", Date.from(Instant.now()), " ", " ", 2L, 1L, " ", " ");
        List<Tweet> list = new ArrayList<>();
        list.add(tweet1);
        list.add(tweet2);
        when(service.getHomeTimeline(twitter)).thenReturn(list);

        mock.perform(get("/hometimeline"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeline"));

        List<Tweet> listGot = service.getHomeTimeline(twitter);
        assertEquals(list.size(), listGot.size());
    }
}