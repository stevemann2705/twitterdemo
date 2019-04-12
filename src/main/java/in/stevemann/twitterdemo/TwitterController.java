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
//        model.addAttribute("tweetId", tweetId);
//        model.addAttribute("tweetText", tweet.getUnmodifiedText());
//        model.addAttribute("tweetUser", tweet.getUser().getName());
//        model.addAttribute("tweetRetweetCount", tweet.getRetweetCount());
//        model.addAttribute("tweetFavoriteCount", tweet.getFavoriteCount());
        return "redirect:" + tweet.getText().substring(tweet.getText().length() - 23);
//        return "showtweet";
    }
}
