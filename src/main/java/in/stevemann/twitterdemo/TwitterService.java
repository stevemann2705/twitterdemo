package in.stevemann.twitterdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void tweet(Twitter twitter, String tweetText) {
        try {
            twitter.timelineOperations().updateStatus(tweetText);
        } catch (RuntimeException ex) {
            logger.error("Unable to tweet" + tweetText, ex);
        }
    }

    public Tweet getTweet(Twitter twitter, String tweetId) {
        try {
            Tweet tweet = twitter.timelineOperations().getStatus(Long.valueOf(tweetId));
            return tweet;
        } catch (RuntimeException ex) {
            logger.error("Unable to get tweet:" + tweetId, ex);
        }
        return null;
    }

    public List<Tweet> getHomeTimeline(Twitter twitter) {
        try {
            List<Tweet> list = twitter.timelineOperations().getHomeTimeline();
            return list;
        } catch (RuntimeException ex) {
            logger.error("Unable to get timeline:" + ex);
        }
        return null;
    }

    public List<Tweet> getFavorites(Twitter twitter) {
        try {
            List<Tweet> list = twitter.timelineOperations().getFavorites();
            return list;
        } catch (RuntimeException ex) {
            logger.error("Unable to get timeline:" + ex);
        }
        return null;
    }

    public List<Tweet> getUserTimeline(Twitter twitter, String user) {
        try {
            List<Tweet> list = twitter.timelineOperations().getUserTimeline(user);
            return list;
        } catch (RuntimeException ex) {
            logger.error("Unable to get timeline:" + ex);
        }
        return null;
    }

    public List<Tweet> getUserFavorites(Twitter twitter, String user) {
        try {
            List<Tweet> list = twitter.timelineOperations().getFavorites(user);
            return list;
        } catch (RuntimeException ex) {
            logger.error("Unable to get timeline:" + ex);
        }
        return null;
    }
}