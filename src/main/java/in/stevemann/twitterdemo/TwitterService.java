package in.stevemann.twitterdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

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
}