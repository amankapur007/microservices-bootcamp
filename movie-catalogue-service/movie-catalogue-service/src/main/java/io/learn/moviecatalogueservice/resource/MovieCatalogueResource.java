package io.learn.moviecatalogueservice.resource;

import io.learn.moviecatalogueservice.model.Catalogue;
import io.learn.moviecatalogueservice.model.Movie;
import io.learn.moviecatalogueservice.model.Rating;
import io.learn.moviecatalogueservice.model.UserRating;
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
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;
    @RequestMapping("/{userId}")
    public List<Catalogue> getCatalogue(@PathVariable String userId){

        UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId,
                UserRating.class);
        return userRating.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(),
                    Movie.class);

            return new Catalogue(movie.getName(), "Test", rating.getRating());
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
