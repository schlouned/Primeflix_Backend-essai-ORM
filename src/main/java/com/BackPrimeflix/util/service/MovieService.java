package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.MovieDto;
import com.BackPrimeflix.model.MovieEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface MovieService {
    List<MovieEntity> getAllMovies();
    Set<MovieEntity> getMoviesFilter(String filter , String language);
    List<MovieEntity> getMoviesByCategory(String filter);
    MovieEntity getMovieById(String id);
    MovieDto convertToMovieDto(MovieEntity movieEntity);
    boolean checkMovieStockQuantity(MovieEntity movie, Integer quantity);
    void movieReduceQuantity(MovieEntity movie, Integer buyQuantity);
    MovieEntity save(MovieEntity movieEntity);
}
