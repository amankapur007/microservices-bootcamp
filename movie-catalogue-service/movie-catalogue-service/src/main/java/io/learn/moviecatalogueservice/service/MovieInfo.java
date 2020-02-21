package io.learn.moviecatalogueservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.learn.moviecatalogueservice.model.Catalogue;
import io.learn.moviecatalogueservice.model.Movie;
import io.learn.moviecatalogueservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogueItem")
    public Catalogue getCatalogueItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(),
                Movie.class);
        return new Catalogue(movie.getName(), "Test", rating.getRating());
    }

    public Catalogue getFallbackCatalogueItem(Rating rating) {
        return new Catalogue("Movie not found","",rating.getRating());
    }

}
