package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.*;
import com.BackPrimeflix.model.*;
import com.BackPrimeflix.repository.ActorRepository;
import com.BackPrimeflix.repository.CategoryRepository;
import com.BackPrimeflix.repository.MovieRepository;
import com.BackPrimeflix.repository.ProductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service("movieService")
public class ImplMovieService implements MovieService {
    //members
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private ProductorRepository productorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    //methods
    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<MovieEntity> getMoviesByCategory(String filter) {
        //recover the category
        CategoryEntity category = categoryRepository.findCategoryEntityByNameEquals(filter);
        //return movies
        return movieRepository.findMovieEntitiesByCategoryIs(category);
    }

    public Set<MovieEntity> getMoviesFilter(String filter, String language) {
        //members
        Set<MovieEntity> movies = new HashSet<>();
        Set<ActorEntity> actors = new HashSet<>();
        Set<ProductorEntity> productors = new HashSet<>();
        Set<MovieEntity> moviesTemp;

        //find movies by actor
        actors.addAll(actorRepository.findActorEntityByLastNameLikeIgnoreCase("%" + filter + "%"));
        actors.addAll(actorRepository.findActorEntityByFirstNameLikeIgnoreCase("%" + filter + "%"));
        moviesTemp = movieRepository.findMovieEntitiesByActorsIn(actors);
        movies.addAll(moviesTemp);
        //find movies by productor
        productors.addAll(productorRepository.findProductorEntityByLastNameLikeIgnoreCase("%" + filter + "%"));
        productors.addAll(productorRepository.findProductorEntityByFirstNameLikeIgnoreCase("%" + filter + "%"));
        moviesTemp.clear();
        moviesTemp = movieRepository.findMovieEntitiesByProductorIn(productors);
        movies.addAll(moviesTemp);

        //find movie by title
        moviesTemp.clear();
        if (language.equals("en")) {
            moviesTemp = movieRepository.findMovieEntitiesByTitleEnLikeIgnoreCase("%" + filter + "%");
        }
        if (language.equals("fr")) {
            moviesTemp = movieRepository.findMovieEntitiesByTitleFrLikeIgnoreCase("%" + filter + "%");
        }
        movies.addAll(moviesTemp);

        //return
        return movies;
    }

    //get movie by movie id
    public MovieEntity getMovieById(String id) {
        return movieRepository.findMovieEntitiesById(Long.parseLong(id));
    }

    //convert to dto
    public MovieDto convertToMovieDto(MovieEntity movieEntity) {
        //create a new dto
        MovieDto movieDto = new MovieDto();
        //set the dto
        //id
        movieDto.setId(movieEntity.getId().toString());
        //productor
        ProductorDto productorDto = new ProductorDto();
        productorDto.setLastName(movieEntity.getProductor().getLastName());
        productorDto.setFirstName(movieEntity.getProductor().getFirstName());
        movieDto.setProductor(productorDto);
        //actors
        List<ActorDto> actorDtos = new ArrayList<>();
        for (ActorEntity actorEntity : movieEntity.getActors()) {
            ActorDto actorDto = new ActorDto();
            actorDto.setLastName(actorEntity.getLastName());
            actorDto.setFirstName(actorEntity.getFirstName());
            actorDtos.add(actorDto);
        }
        movieDto.setActors(actorDtos);
        //release year
        movieDto.setReleaseYear(movieEntity.getReleaseYear().toString());
        //title
        movieDto.setTitleEn(movieEntity.getTitleEn());
        movieDto.setTitlefr(movieEntity.getTitleFr());
        //category
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(movieEntity.getCategory().getId().toString());
        categoryDto.setName(movieEntity.getCategory().getName());
        movieDto.setCategory(categoryDto);
        //discount
        //*********************************************************************
        DiscountDto discountDto = new DiscountDto();
        //recover discounts
        Set<DiscountEntity> discounts = movieEntity.getCategory().getDiscounts();
        //check if the discount is available today
        Date date = new Date();
        for (DiscountEntity discount : discounts) {
            if (discount.getStartDate().before(date) && discount.getEndDate().after(date)) {
                discountDto.setId(discount.getId().toString());
                discountDto.setPercentage(discount.getPercentage().toString());
                discountDto.setCategory(categoryService.convertToCategoryDto(discount.getCategory()));
                discountDto.setStartDate(discount.getStartDate().toString());
                discountDto.setEndDate(discount.getEndDate().toString());
                movieDto.getCategory().setDiscount(discountDto);
            }
        }

        //*********************************************************************
        //picture url
        movieDto.setPictureUrl(movieEntity.getPictureUrl());
        //summaryEn and  Fr
        movieDto.setSummaryEn(movieEntity.getSummaryEn());
        movieDto.setSummaryFr(movieEntity.getSummaryFr());
        //duration
        if (movieEntity.getDuration() == null)
            movieDto.setDuration("0");
        else
            movieDto.setDuration(movieEntity.getDuration().toString());
        //price
        if (movieEntity.getPrice() == null)
            movieDto.setPrice("0");
        else
            movieDto.setPrice(movieEntity.getPrice().toString());
        //stock quantity
        if (movieEntity.getStockQuantity() == null)
            movieDto.setStockQuantity(0);
        else
            movieDto.setStockQuantity(movieEntity.getStockQuantity());
        //return
        return movieDto;
    }

    public boolean checkMovieStockQuantity(MovieEntity movie, Integer quantity) {
        if (movie.getStockQuantity() < quantity)
            return false;
        else
            return true;
    }

    public void movieReduceQuantity(MovieEntity movie, Integer buyQuantity) {
        Integer formerQuantity = movie.getStockQuantity();
        Integer newQuantity = formerQuantity - buyQuantity;
        movie.setStockQuantity(newQuantity);
        //save the new quantity
        movieRepository.save(movie);
    }

    public MovieEntity save(MovieEntity movieEntity) {
        return movieRepository.save(movieEntity);
    }


}
