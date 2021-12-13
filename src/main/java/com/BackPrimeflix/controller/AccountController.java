package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.UserDto;
import com.BackPrimeflix.exception.AccountCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.AddressEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.response.Response;
import com.BackPrimeflix.response.UserResponse;
import com.BackPrimeflix.util.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/account")
public class AccountController {
    //members
    @Autowired
    private UserService userService;

    @PostMapping("/updateUserInformations")
    public ResponseEntity<UserResponse> modifyUserInformations(@RequestBody String json, Authentication auth) {
        //members
        UserResponse resp = new UserResponse();
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
            //recover new informations and put it in the logged user
            ObjectMapper mapper = new ObjectMapper();
            UserDto userDto = mapper.readValue(json, UserDto.class);
            loggedUser.setLastName(userDto.getLastName());
            loggedUser.setFirstName(userDto.getFirstName());

            AddressEntity addressEntity = loggedUser.getAddressEntity();
            addressEntity.setStreet(userDto.getAddress().getStreet());
            addressEntity.setHouseNumber(userDto.getAddress().getHouseNumber());
            addressEntity.setZipCode(userDto.getAddress().getZipCode());
            addressEntity.setCity(userDto.getAddress().getCity());
            addressEntity.setCountryCode(userDto.getAddress().getCountryCode());

            loggedUser.setAddressEntity(addressEntity);
            //persist in the database
            userService.save(loggedUser);
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.USER_INFO_UPDATED);
            alertObject.setAlertCode(ResponseCode.USER_ACCOUNT_UPDATED);
            alertObject.setAlertMessage(ResponseCode.USER_ACCOUNT_UPDATED_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());

        }
        //if error
        catch (Exception e) {
            throw new AccountCustomException(ResponseCode.USER_ACCOUNT_UPDATE_PROBLEM);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<UserResponse>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUserInformations")
    public ResponseEntity<Response> getUserInformations(Authentication auth) {
        //members
        Response resp = new Response();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if(!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //create a map where we can put each user informations
            HashMap<String, String> map = new HashMap<>();
            map.put(WebConstants.USER_LAST_NAME, loggedUser.getLastName());
            map.put(WebConstants.USER_FIRST_NAME, loggedUser.getFirstName());
            map.put(WebConstants.USER_STREET, loggedUser.getAddressEntity().getStreet());
            map.put(WebConstants.USER_HOUSE_NUMBER, loggedUser.getAddressEntity().getHouseNumber());
            map.put(WebConstants.USER_ZIP_CODE, loggedUser.getAddressEntity().getZipCode());
            map.put(WebConstants.USER_CITY, loggedUser.getAddressEntity().getCity());
            map.put(WebConstants.USER_COUNTRY_CODE, loggedUser.getAddressEntity().getCountryCode());
            resp.setMap(map);

            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.USER_INFO_OK);
        } catch (Exception e) {
            throw new AccountCustomException(ResponseCode.USER_ACCOUNT_GET_PROBLEM);
        }
        //return the response
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    @GetMapping("/getCompanyAddress")
    public ResponseEntity<Response> getCompanyAddress(Authentication auth) {
        //members
        Response resp = new Response();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //recover admin user
        UserEntity adminUser = userService.getByEmail(WebConstants.COMPANY_EMAIL);
        try {
            //create a map where we can put address information
            HashMap<String, String> map = new HashMap<>();
            map.put(WebConstants.COMPANY_NAME, WebConstants.COMPANY_NAME_VALUE);
            map.put(WebConstants.USER_STREET, adminUser.getAddressEntity().getStreet());
            map.put(WebConstants.USER_HOUSE_NUMBER, adminUser.getAddressEntity().getHouseNumber());
            map.put(WebConstants.USER_ZIP_CODE, adminUser.getAddressEntity().getZipCode());
            map.put(WebConstants.USER_CITY, adminUser.getAddressEntity().getCity());
            map.put(WebConstants.USER_COUNTRY_CODE, adminUser.getAddressEntity().getCountryCode());
            resp.setMap(map);

            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.GET_COMPANY_SUCCESS);
        } catch (Exception e) {
            throw new AccountCustomException(ResponseCode.GET_COMPANY_ERROR);
        }
        //return the response
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }
}
