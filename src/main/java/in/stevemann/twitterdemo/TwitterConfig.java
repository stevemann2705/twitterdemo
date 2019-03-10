package in.stevemann.twitterdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:twitter.properties")
public class TwitterConfig {
    @Value("${stevemann2705.consumerKey}")
    private String consumerKey;

    @Value("${stevemann2705.consumerSecret}")
    private String consumerSecret;

    @Value("${stevemann2705.accessToken}")
    private String accessToken;

    @Value("${stevemann2705.accessTokenSecret}")
    private String accessTokenSecret;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
