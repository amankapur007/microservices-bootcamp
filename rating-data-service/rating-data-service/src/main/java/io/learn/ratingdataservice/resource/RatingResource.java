package io.learn.ratingdataservice.resource;

import io.learn.ratingdataservice.model.Rating;
import io.learn.ratingdataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId){
        return new Rating(movieId,4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable String userId){
        List<Rating> userRatingList = Arrays.asList(
                new Rating("1234",4),
                new Rating("5678",3)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRating(userRatingList);
        return  userRating;
    }
}
