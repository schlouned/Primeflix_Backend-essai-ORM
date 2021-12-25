package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.UserDto;
import com.BackPrimeflix.exception.PersonCrudCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.response.PersonResponse;
import com.BackPrimeflix.util.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/user")
public class UserController {
    //members
    @Autowired
    private UserService userService;

    //methods
    @PostMapping("/save-user")
    public ResponseEntity<PersonResponse> saveUser(@RequestBody String json, Authentication auth) {
        //members
        PersonResponse resp = new PersonResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_ADMIN_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //recover new information and put it in the logged user
            ObjectMapper mapper = new ObjectMapper();
            UserDto userDto = mapper.readValue(json, UserDto.class);
            //save new user
            userService.saveUser(userDto);

        }
        //if error
        catch (Exception e) {
            throw new PersonCrudCustomException(ResponseCode.PERSON_CRUD_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<PersonResponse>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/users-list")
    public ResponseEntity<PersonResponse> getUsers(Authentication auth) {
        //members
        PersonResponse resp = new PersonResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_ADMIN_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //recover user list
            List<UserEntity> users = userService.getUsersList();
            //convert to Dto
            List<UserDto> userDtos = new ArrayList<>();
            for(UserEntity user: users){
                UserDto userDto = userService.convertToUserDto(user);
                userDtos.add(userDto);
            }
            resp.setUsers(userDtos);
        }
        //if error
        catch (Exception e) {
            throw new PersonCrudCustomException(ResponseCode.PERSON_CRUD_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<PersonResponse>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<PersonResponse> deleteUser(@RequestBody String json, Authentication auth) {
        //members
        PersonResponse resp = new PersonResponse();
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
            //recover the id
            ObjectMapper mapper = new ObjectMapper();
            UserDto userDto = mapper.readValue(json, UserDto.class);
            String id = userDto.getId();
            //delete user

            userService.deleteUser(Long.parseLong(id));
        }
        //if error
        catch (Exception e) {
            throw new PersonCrudCustomException(ResponseCode.PERSON_CRUD_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<PersonResponse>(resp, HttpStatus.ACCEPTED);
    }


}



