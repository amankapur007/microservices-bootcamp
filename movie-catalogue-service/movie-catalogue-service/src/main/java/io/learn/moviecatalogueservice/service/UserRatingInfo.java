package io.learn.moviecatalogueservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.learn.moviecatalogueservice.model.Catalogue;
import io.learn.moviecatalogueservice.model.Rating;
import io.learn.moviecatalogueservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(@PathVariable String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId,
                UserRating.class);
    }

    public UserRating getFallbackUserRating(@PathVariable String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRating(Arrays.asList(new Rating("0",0)));
        return userRating;
    }

}
