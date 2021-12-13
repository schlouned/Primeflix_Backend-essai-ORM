package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.*;
import com.BackPrimeflix.exception.MovieCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.ActorEntity;
import com.BackPrimeflix.model.DiscountEntity;
import com.BackPrimeflix.model.MovieEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.response.MovieResponse;
import com.BackPrimeflix.util.service.CategoryService;
import com.BackPrimeflix.util.service.MovieService;
import com.BackPrimeflix.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/movies")
public class MovieController {
    //members
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    //methods
    @GetMapping("/getAllMovies")
    public ResponseEntity<MovieResponse> getAllMovies(Authentication auth) {
        //members
        MovieResponse resp = new MovieResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        try {
            //recover movies
            System.out.println("***********\n" + movieService.getAllMovies() + "\n**********");
            //***convert movies to dto
            resp.setMovies(this.convertMovieEntityToDto(movieService.getAllMovies()));
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.MOVIES_GETALL_SUCCESS);
        } catch (Exception e) {
            throw new MovieCustomException(ResponseCode.MOVIES_GETALL_ERROR);
        }
        //return the response
        return new ResponseEntity<MovieResponse>(resp, HttpStatus.OK);
    }

    //methods
    @GetMapping("/getMoviesByCategory")
    public ResponseEntity<MovieResponse> getMoviesByCategory(Authentication auth, String filter, String language) {
        //members
        MovieResponse resp = new MovieResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //search movies according the filter: category
            //***convert movies to dto
            resp.setMovies(this.convertMovieEntityToDto(movieService.getMoviesByCategory(filter)));
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.MOVIES_GETMOVIECATEGORY_SUCCESS);
        } catch (Exception e) {
            throw new MovieCustomException(ResponseCode.MOVIES_GETMOVIECATEGORY_ERROR);
        }
        //return the response
        return new ResponseEntity<MovieResponse>(resp, HttpStatus.OK);
    }

    @GetMapping("/getMoviesFilter")
    public ResponseEntity<MovieResponse> getMovieFilter(Authentication auth, String filter, String language) {
        //members
        MovieResponse resp = new MovieResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //search movies according the filter: the filter will be apply on the title, actor name, productor name
            //***convert movies to dto
            resp.setMovies(this.convertMovieEntityToDto(new ArrayList<MovieEntity>(movieService.getMoviesFilter(filter, language))));
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.MOVIES_GETFILTER_SUCCESS);
        } catch (Exception e) {
            throw new MovieCustomException(ResponseCode.MOVIES_GETFILTER_ERROR);
        }
        //return the response
        return new ResponseEntity<MovieResponse>(resp, HttpStatus.OK);
    }

    //*****************************************************************************************************
    //util convert movie to dto
    public List<MovieDto> convertMovieEntityToDto(List<MovieEntity> movies) {
        //create a new list
        List<MovieDto> moviesDto = new ArrayList<>();
        //loop
        for (MovieEntity movie : movies) {
            //create a new dto
            MovieDto movieDto = new MovieDto();
            //set the dto
            //id
            movieDto.setId(movie.getId().toString());
            //productor
            ProductorDto productorDto = new ProductorDto();
            productorDto.setLastName(movie.getProductor().getLastName());
            productorDto.setFirstName(movie.getProductor().getFirstName());
            movieDto.setProductor(productorDto);
            //actors
            List<ActorDto> actorDtos = new ArrayList<>();
            for (ActorEntity actorEntity : movie.getActors()) {
                ActorDto actorDto = new ActorDto();
                actorDto.setLastName(actorEntity.getLastName());
                actorDto.setFirstName(actorEntity.getFirstName());
                actorDtos.add(actorDto);
            }
            movieDto.setActors(actorDtos);
            //release year
            movieDto.setReleaseYear(movie.getReleaseYear().toString());
            //title
            movieDto.setTitleEn(movie.getTitleEn());
            movieDto.setTitlefr(movie.getTitleFr());
            //category
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(movie.getCategory().getId().toString());
            categoryDto.setName(movie.getCategory().getName());
            movieDto.setCategory(categoryDto);
            //discount
            //*********************************************************************
            DiscountDto discountDto = new DiscountDto();
            //recover discounts
            Set<DiscountEntity> discounts = movie.getCategory().getDiscounts();
            //check if the discount is available today
            Date date = new Date();
            Timestamp today = new Timestamp(date.getTime());
            for (DiscountEntity discount : discounts) {
                if (discount.getStartDate().before(date) && discount.getEndDate().after(date)) {
                    discountDto.setId(discount.getId().toString());
                    discountDto.setPercentage(discount.getPercentage().toString());
                    discountDto.setStartDate(discount.getStartDate().toString());
                    discountDto.setEndDate(discount.getEndDate().toString());
                    discountDto.setCategory(categoryService.convertToCategoryDto(discount.getCategory()));
                    movieDto.getCategory().setDiscount(discountDto);
                }
            }

            //*********************************************************************
            //picture url
            movieDto.setPictureUrl(movie.getPictureUrl());
            //summaryEn and  Fr
            movieDto.setSummaryEn(movie.getSummaryEn());
            movieDto.setSummaryFr(movie.getSummaryFr());
            //duration
            if (movie.getDuration() == null)
                movieDto.setDuration("0");
            else
                movieDto.setDuration(movie.getDuration().toString());
            //price
            if (movie.getPrice() == null)
                movieDto.setPrice("0");
            else
                movieDto.setPrice(movie.getPrice().toString());
            //stock quantity
            if (movie.getStockQuantity() == null)
                movieDto.setStockQuantity(0);
            else
                movieDto.setStockQuantity(movie.getStockQuantity());
            //add to list
            moviesDto.add(movieDto);
        }
        //return
        return moviesDto;
    }
}
