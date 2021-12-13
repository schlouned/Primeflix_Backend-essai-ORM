package com.BackPrimeflix.controller;

import com.BackPrimeflix.dto.TokenDto;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.util.jwt.JwtUtil;
import com.BackPrimeflix.util.service.AddressService;
import com.BackPrimeflix.util.service.UserService;
import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.exception.UserAlreadyExistException;
import com.BackPrimeflix.exception.AccountCustomException;
import com.BackPrimeflix.repository.EmailRegistrationTokenRepository;
import com.BackPrimeflix.repository.UserRepository;
import com.BackPrimeflix.response.ServerResponse;
import com.BackPrimeflix.security.CustomUserDetailService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/oAuth")
@CrossOrigin
public class OAuthController {
    //members
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtProvider;
    @Autowired
    UserService userService;
    @Autowired
    private JwtUtil jwtutil;
    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private AddressService addressService;
    @Value("${secretPsw}")
    String secretPsw;
    @Value("700839171219-gcjr3tbkv9krq2q4smfcfas1svasj2br")
    String googleClientId;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailRegistrationTokenRepository emailRegistrationTokenRepository;

    //login with facebook
    @PostMapping("/loginByFacebook")
    public ResponseEntity<ServerResponse> facebook(@RequestBody TokenDto tokenDto) throws IOException, UserAlreadyExistException {
        //members
        Facebook facebook = new FacebookTemplate(tokenDto.getValue());
        final String [] fields = {"email"};
        User user = facebook.fetchObject("me", User.class, fields);
        UserEntity userEntity = new UserEntity();
        ServerResponse resp = new ServerResponse();
        AlertObject alertObject = new AlertObject();

        try {
            //user exist or not
            if (userService.checkIfUserExist(user.getEmail())) {
                userEntity = userService.getByEmail(user.getEmail());
                if (!userService.getByEmail(user.getEmail()).isAccountVerified()){
                    throw new AccountCustomException(ResponseCode.USER_CREDENTIAL_INVALID);
                }
            }
            else {
                userEntity = saveSocialUser(user.getEmail());

                //verify user
                userEntity.setAccountVerified(true);
                userRepository.save(userEntity); // let's same user details
                //remove registration token
                emailRegistrationTokenRepository.removeByToken(tokenDto.getValue());
            }

            //create token
            String tokenRes = loginSocial(userEntity.getEmail(), secretPsw);

            //check the role
            final UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());

            //check what kind of role it is
            if (userDetails != null
                    && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                resp.setUserType("ADMIN");
            } else if (userDetails != null
                    && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("MARKETINGMANAGER"))) {
                resp.setUserType("MARKETINGMANAGER");
            } else if (userDetails != null
                    && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("STOREKEEPER"))) {
                resp.setUserType("STOREKEEPER");
            } else {
                resp.setUserType("CLIENT");
            }

            //check if there is a cart and how full it is
            resp.setCartItemsNumber(userService.checkCart(userEntity).toString());
            //prepare the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
            resp.setEmail(user.getEmail());
            resp.setAuthToken(tokenRes);
            alertObject.setAlertCode(ResponseCode.USER_VERIFIED);
            alertObject.setAlertMessage(ResponseCode.USER_VERIFIED_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
        }
        catch(Exception e){
            throw new AccountCustomException(ResponseCode.USER_FACEBOOK_LOGIN_PROBLEM);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    //login method for oAuth
    private String loginSocial(String email, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new AccountCustomException(ResponseCode.USER_CREDENTIAL_INVALID);
        }
        final UserDetails userDetails = userDetailService.loadUserByUsername(email);
        final String jwt = jwtutil.generateToken(userDetails);

        return jwt;
    }

    //save method for oAuth
    private UserEntity saveSocialUser(String email) throws UserAlreadyExistException {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(secretPsw);
        return userService.register(userEntity, "social");
    }

    //login with google
    @PostMapping("/loginByGoogle")
    public ResponseEntity<ServerResponse> google(@RequestBody TokenDto tokenDto) throws IOException, UserAlreadyExistException {
        //members
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                        .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenDto.getValue());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();

        UserEntity userEntity = new UserEntity();
        ServerResponse resp = new ServerResponse();
        AlertObject alertObject = new AlertObject();

        try {
            //user exist or not
            if (userService.checkIfUserExist(payload.getEmail())) {
                userEntity = userService.getByEmail(payload.getEmail());
                if (!userEntity.isAccountVerified()){
                    throw new AccountCustomException(ResponseCode.USER_CREDENTIAL_INVALID);
                }
            }
            else {
                userEntity = saveSocialUser(payload.getEmail());

                //verify user
                userEntity.setAccountVerified(true);
                userRepository.save(userEntity); // let's same user details
                //remove registration token
                emailRegistrationTokenRepository.removeByToken(tokenDto.getValue());
            }

            //create token
            String tokenRes = loginSocial(userEntity.getEmail(), secretPsw);

            //check the role
            final UserDetails userDetails = userDetailService.loadUserByUsername(userEntity.getEmail());

            //check what kind of role it is
            if (userDetails != null
                    && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                resp.setUserType("ADMIN");
            } else if (userDetails != null
                    && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("MARKETINGMANAGER"))) {
                resp.setUserType("MARKETINGMANAGER");
            } else if (userDetails != null
                    && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("STOREKEEPER"))) {
                resp.setUserType("STOREKEEPER");
            } else {
                resp.setUserType("CLIENT");
            }

            //check if there is a cart and how full it is
            resp.setCartItemsNumber(userService.checkCart(userEntity).toString());
            //prepare the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
            resp.setEmail(userEntity.getEmail());
            resp.setAuthToken(tokenRes);
            alertObject.setAlertCode(ResponseCode.USER_VERIFIED);
            alertObject.setAlertMessage(ResponseCode.USER_VERIFIED_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
        }
        catch(Exception e){
            throw new AccountCustomException(ResponseCode.USER_FACEBOOK_LOGIN_PROBLEM);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity(resp, HttpStatus.OK);
    }

}
