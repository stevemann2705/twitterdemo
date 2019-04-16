package in.stevemann.twitterdemo;

import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

@Component
public class TwitterTemplateCreator {
    @Autowired
    private Environment env;

    private TwitterTemplate twitterTemplate;

    public Twitter getTwitterTemplate(String accountName) {
        String consumerKey = env.getProperty("stevemann2705.consumerKey");
        String consumerSecret = env.getProperty("stevemann2705.consumerSecret");
        String accessToken = env.getProperty("stevemann2705.accessToken");
        String accessTokenSecret = env.getProperty("stevemann2705.accessTokenSecret");
        Preconditions.checkNotNull(consumerKey);
        Preconditions.checkNotNull(consumerSecret);
        Preconditions.checkNotNull(accessToken);
        Preconditions.checkNotNull(accessTokenSecret);

        if (twitterTemplate == null) {
            twitterTemplate =
                    new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        }

        return twitterTemplate;
    }
}
