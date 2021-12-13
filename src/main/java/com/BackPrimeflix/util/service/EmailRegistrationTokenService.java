package com.BackPrimeflix.util.service;


import com.BackPrimeflix.model.EmailRegistrationToken;


public interface EmailRegistrationTokenService {
    EmailRegistrationToken createSecureToken();
    void saveSecureToken(final EmailRegistrationToken token);
    EmailRegistrationToken findByToken(final String token);
    void removeToken(final EmailRegistrationToken token);
    void removeTokenByToken(final String token);
}
