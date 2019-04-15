package in.stevemann.twitterdemo;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TwitterController {
    final TwitterService twitterService;
    final TwitterTemplateCreator twitterTemplateCreator;

    public TwitterController(TwitterService twitterService, TwitterTemplateCreator twitterTemplate) {
        this.twitterService = twitterService;
        this.twitterTemplateCreator = twitterTemplate;
    }

    @RequestMapping("/")
    public String getHomePage(){
        return "home";
    }

    @RequestMapping("/tweet")
    public String makeTweet(@RequestParam(name = "tweet") String tweet, Model model){
        twitterService.tweet(twitterTemplateCreator.getTwitterTemplate("stevemann2705"), tweet);
        model.addAttribute("tweet", tweet);
        return "submitted";
    }

    @RequestMapping("/gettweet/{tweetId}")
    public String getTweet(@PathVariable String tweetId, Model model) {
        Tweet tweet = twitterService.getTweet(twitterTemplateCreator.getTwitterTemplate("stevemann2705"), tweetId);
        model.addAttribute("tweetId", tweetId);
        /*
        Spring Social Project is dead.
        Development stopped before the charachter limit on Twitter was increased from 140 to 280.
        Hence, the Tweet object expects text of the tweet to be not more than 140.
        For any tweet with more than 140 charachters, Twitter API now truncates the tweet and adds a link to the tweet at the end.
        To get the full text of the tweet, add parameter tweet_mode with value extended to your request parameters. (Handled by Spring Social)
        For more info: https://stackoverflow.com/a/40454382
         */
        model.addAttribute("tweetText", tweet.getUnmodifiedText());
        model.addAttribute("tweetUser", tweet.getUser().getName());
        model.addAttribute("tweetRetweetCount", tweet.getRetweetCount());
        model.addAttribute("tweetFavoriteCount", tweet.getFavoriteCount());
        //return "redirect:" + tweet.getText().substring(tweet.getText().length() - 23);
        return "showtweet";
    }
}
