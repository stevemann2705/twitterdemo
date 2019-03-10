package in.stevemann.twitterdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}
