package io.learn.moviecatalogueservice.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import io.learn.moviecatalogueservice.model.Catalogue;
import io.learn.moviecatalogueservice.model.Movie;
import io.learn.moviecatalogueservice.model.Rating;
import io.learn.moviecatalogueservice.model.UserRating;
import io.learn.moviecatalogueservice.service.MovieInfo;
import io.learn.moviecatalogueservice.service.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogue")
public class MovieCatalogueResource {

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @Autowired
    private WebClient.Builder webClientBuilder;
    @RequestMapping("/{userId}")
    public List<Catalogue> getCatalogue(@PathVariable String userId){

        UserRating userRating = userRatingInfo.getUserRating(userId);
        return userRating.getUserRating().stream().map(rating -> {
            return movieInfo.getCatalogueItem(rating);
        }).collect(Collectors.toList());
    }

}

/*Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/"+rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
             */
