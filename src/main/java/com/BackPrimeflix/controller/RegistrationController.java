package com.BackPrimeflix.controller;

import com.BackPrimeflix.model.AddressEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.util.Validator;
import com.BackPrimeflix.util.service.AddressService;
import com.BackPrimeflix.util.service.UserService;
import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.exception.InvalidTokenException;
import com.BackPrimeflix.exception.AccountCustomException;
import com.BackPrimeflix.response.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/registration")
public class RegistrationController {
    //members
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AddressService addressService;

    //registration method
    @PostMapping("/registrationByEmail")
    public ResponseEntity<ServerResponse> registerNewUserByEmail(@RequestBody UserEntity userEntity) {
        //members
        ServerResponse resp = new ServerResponse();
        AlertObject alertObject = new AlertObject();

        try {
            //check if important fields are not empty
            if (Validator.isUserEmpty(userEntity)) {
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
                alertObject.setAlertCode(ResponseCode.USER_NOT_COMPLETED);
                alertObject.setAlertMessage(ResponseCode.USER_NOT_COMPLETED_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());
            }
            //check if customer is unique
            else if(userService.checkIfUserExist(userEntity.getEmail())){
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
                alertObject.setAlertCode(ResponseCode.USER_ALREADY_EXIST);
                alertObject.setAlertMessage(ResponseCode.USER_ALREADY_EXIST_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());
            }
            //if informations are correct and unique user
             else {
                resp.setStatus(ResponseCode.SUCCESS_CODE);
                resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
                alertObject.setAlertCode(ResponseCode.USER_REG);
                alertObject.setAlertMessage(ResponseCode.USER_REG_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
                //persist in the database
                UserEntity user = userService.register(userEntity, "email");

            }
             //set the error object
            resp.setAlertObject(alertObject);
        }
        //if error when creating the user
        catch (Exception e) {
            throw new AccountCustomException(ResponseCode.USER_CREATING_ACCOUNT_PROBLEM);
        }

        //send response
        return new ResponseEntity<ServerResponse>(resp, HttpStatus.ACCEPTED);
    }

    //verify account by email method
    @GetMapping("/accountVerificationByEmail")
    public ResponseEntity<ServerResponse> verifyCustomer(@RequestParam(required = false) String token){
        //members
        ServerResponse resp = new ServerResponse();
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            //case if no token
            if (StringUtils.isEmpty(token)) {
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE_ERROR_TOKEN);
                resp.setMessage(ResponseCode.USER_HAS_NO_TOKEN);
                return new ResponseEntity<ServerResponse>(resp, HttpStatus.ACCEPTED);
            }
            //case if invalid token
            try {
                userService.verifyUser(token);
            } catch (InvalidTokenException e) {
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE_ERROR_TOKEN);
                resp.setMessage(ResponseCode.USER_HAS_INVALID_TOKEN);
                return new ResponseEntity<ServerResponse>(resp, HttpStatus.ACCEPTED);
            }
            //case if success
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.USER_TOKEN_OK);
            //redirection
            URI location = new URI("https://localhost:4200/accountVerified");
            responseHeaders.setLocation(location);
        }
        catch(Exception e){
            throw new AccountCustomException(ResponseCode.USER_VERIFIED_ACCOUNT_PROBLEM);
        }
        //send response
        return new ResponseEntity<ServerResponse>(resp, responseHeaders, HttpStatus.PERMANENT_REDIRECT);
    }



}
