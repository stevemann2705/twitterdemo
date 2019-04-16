package in.stevemann.twitterdemo;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TwitterController {
    final TwitterService twitterService;
    final TwitterTemplateCreator twitterTemplateCreator;

    private Twitter twitter;

    public TwitterController(TwitterService twitterService, TwitterTemplateCreator twitterTemplate) {
        this.twitterService = twitterService;
        this.twitterTemplateCreator = twitterTemplate;

        twitter = twitterTemplateCreator.getTwitterTemplate("stevemann2705");
    }

    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/tweet")
    public String makeTweet(@RequestParam(name = "tweet") String tweet, Model model){
        twitterService.tweet(twitter, tweet);
        model.addAttribute("tweet", tweet);
        return "submitted";
    }

    @GetMapping("/gettweet/{tweetId}")
    public String getTweet(@PathVariable String tweetId, Model model) {
        Tweet tweet = twitterService.getTweet(twitter, tweetId);
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

    @GetMapping("/hometimeline")
    public String getHomeTimeline(Model model) {
        List<Tweet> tweetList = twitterService.getHomeTimeline(twitter);
        model.addAttribute("tweetList", tweetList);
        return "timeline";
    }

    @GetMapping("/favorites")
    public String getFavorites(Model model) {
        List<Tweet> tweetList = twitterService.getFavorites(twitter);
        model.addAttribute("tweetList", tweetList);
        return "timeline";
    }

    @GetMapping("/{user}/timeline")
    public String getUserTimeline(@PathVariable String user, Model model) {
        List<Tweet> tweetList = twitterService.getUserTimeline(twitter, user);
        model.addAttribute("tweetList", tweetList);
        return "timeline";
    }

    @GetMapping("/{user}/favorites")
    public String getUserFavorites(@PathVariable String user, Model model) {
        List<Tweet> tweetList = twitterService.getUserFavorites(twitter, user);
        model.addAttribute("tweetList", tweetList);
        return "timeline";
    }
}
