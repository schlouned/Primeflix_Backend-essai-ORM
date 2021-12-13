package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.BlackListToken;
import com.BackPrimeflix.repository.BlackListTokenRepository;
import com.BackPrimeflix.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class ImplBlackListTokenService implements BlackListTokenService{
    //members
    @Autowired
    BlackListTokenRepository blackListTokenRepository;

    @Autowired
    JwtUtil jwtUtil;

    //get token
    public String getTokenFromAuth(HttpServletRequest request){
        //recover the token from the request
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);

        //return token
        return jwt;
    }

    //save token in black list
    public void saveTokenToBlackList(String token) {
        //recover the expiration date
        Date expirationDate = jwtUtil.extractExpiration(token);

        BlackListToken blackListToken = new BlackListToken();
        blackListToken.setToken(token);
        blackListToken.setExpireAt(expirationDate);

        //save in DB
        blackListTokenRepository.save(blackListToken);
    }

    //is token black listed
    public boolean isTokenBlackListed(String token){
        //recover the token
        BlackListToken blackListToken = blackListTokenRepository.findByToken(token);
        if(blackListToken != null && blackListToken.getToken().equals(token))
            return true;
        else
            return false;
    }
}
