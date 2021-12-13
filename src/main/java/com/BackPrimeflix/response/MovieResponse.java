package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.MovieDto;
import com.BackPrimeflix.model.MovieEntity;

import java.util.List;

public class MovieResponse extends Response{
    //members
    private List<MovieDto> movies;

    //getters setters
    public List<MovieDto> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDto> movies) {
        this.movies = movies;
    }
}
