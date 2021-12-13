package com.BackPrimeflix.util.service;

import javax.servlet.http.HttpServletRequest;

public interface BlackListTokenService {
    String getTokenFromAuth(HttpServletRequest request);
    void saveTokenToBlackList(String token);
    boolean isTokenBlackListed(String token);
}
