package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.CategoryDto;
import com.BackPrimeflix.exception.CategoryCustomException;
import com.BackPrimeflix.exception.MovieCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.CategoryEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.response.CategoryResponse;
import com.BackPrimeflix.response.MovieResponse;
import com.BackPrimeflix.util.service.CategoryService;
import com.BackPrimeflix.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/category")
public class CategoryController {
    //members
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    //methods
    @GetMapping("/getAllCategories")
    public ResponseEntity<CategoryResponse> getAllCategories(Authentication auth) {
        //members
        CategoryResponse resp = new CategoryResponse();
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
            //***recover and convert categories to dto
            List<CategoryEntity> categories = categoryService.getAllCategories();
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for (CategoryEntity categoryEntity : categories) {
                categoryDtos.add(categoryService.convertToCategoryDto(categoryEntity));
            }
            resp.setCategories(categoryDtos);
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.GET_CATEGORY_SUCCESS);
        } catch (Exception e) {
            throw new CategoryCustomException(ResponseCode.GET_CATEGORY_ERROR);
        }
        //return the response
        return new ResponseEntity<CategoryResponse>(resp, HttpStatus.OK);
    }
}
