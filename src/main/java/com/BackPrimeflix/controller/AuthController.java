package com.BackPrimeflix.controller;

import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.util.jwt.JwtUtil;
import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.exception.AccountCustomException;
import com.BackPrimeflix.response.ServerResponse;
import com.BackPrimeflix.security.CustomUserDetailService;
import com.BackPrimeflix.util.service.BlackListTokenService;
import com.BackPrimeflix.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/authentication")
public class AuthController {
    //members
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtutil;

    @Autowired
    private BlackListTokenService blackListTokenService;

    @Autowired
    private UserService userService;

    //login by email
    @PostMapping("/loginByEmail")
    public ResponseEntity<ServerResponse> loginByEmail(@RequestBody HashMap<String, String> credential) {
        //recover email and password from http request (user)
        final String email = credential.get(WebConstants.USER_EMAIL);
        final String password = credential.get(WebConstants.USER_PASSWORD);
        //prepare the response
        ServerResponse resp = new ServerResponse();
        AlertObject alertObject = new AlertObject();

        try {
            //check if the account is validated
            if (userService.getByEmail(email).isAccountVerified()) {
                //authentication
                try {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                } catch (BadCredentialsException e) {
                    throw new AccountCustomException(ResponseCode.USER_CREDENTIAL_INVALID);
                }
                //create token
                final UserDetails userDetails = userDetailService.loadUserByUsername(email);
                final String jwt = jwtutil.generateToken(userDetails);

                //set response and send the token in the response
                resp.setStatus(ResponseCode.SUCCESS_CODE);
                resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
                resp.setEmail(email);
                resp.setAuthToken(jwt);

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
                resp.setCartItemsNumber(userService.checkCart(userService.getByEmail(email)).toString());
                //alert
                alertObject.setAlertCode(ResponseCode.USER_VERIFIED);
                alertObject.setAlertMessage(ResponseCode.USER_VERIFIED_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
            }//end of the check account verified
            else {
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                resp.setMessage(ResponseCode.USER_NOT_VERIFIED);
                alertObject.setAlertCode(ResponseCode.USER_NOT_VERIFIED);
                alertObject.setAlertMessage(ResponseCode.USER_NOT_VERIFIED_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());
            }
            //set alert object
            resp.setAlertObject(alertObject);
        }
        catch(Exception e){
            throw new AccountCustomException(e.getMessage());
        }
        //return response
        return new ResponseEntity<ServerResponse>(resp, HttpStatus.OK);
    }

    //logout
    @GetMapping(value = "/logout")
    public ResponseEntity<ServerResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        //member
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ServerResponse resp = new ServerResponse();
        AlertObject alertObject = new AlertObject();

        //logout and put the jwt in the black list
        try {
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
                //recover token
                String token = blackListTokenService.getTokenFromAuth(request);
                //put token in black list
                blackListTokenService.saveTokenToBlackList(token);
            }
            //set response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
            alertObject.setAlertCode(ResponseCode.USER_LOGGED_OUT);
            alertObject.setAlertMessage(ResponseCode.USER_LOGGED_OUT_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
            resp.setAlertObject(alertObject);
        }
        catch(Exception e){
            throw new AccountCustomException(ResponseCode.USER_LOGGED_OUT);
        }

        //return response
        return new ResponseEntity<ServerResponse>(resp, HttpStatus.OK);
    }
}
