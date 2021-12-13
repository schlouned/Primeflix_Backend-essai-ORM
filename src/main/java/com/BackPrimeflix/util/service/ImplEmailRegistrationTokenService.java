package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.EmailRegistrationToken;
import com.BackPrimeflix.repository.EmailRegistrationTokenRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@Service
public class ImplEmailRegistrationTokenService implements EmailRegistrationTokenService {
    //members
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Value("${jdj.secure.token.validity}")
    private int tokenValidityInSeconds;

    @Autowired
    EmailRegistrationTokenRepository emailRegistrationTokenRepository;

    @Override
    public EmailRegistrationToken createSecureToken(){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        EmailRegistrationToken emailRegistrationToken = new EmailRegistrationToken();
        emailRegistrationToken.setToken(tokenValue);
        emailRegistrationToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveSecureToken(emailRegistrationToken);
        return emailRegistrationToken;
    }

    @Override
    public void saveSecureToken(EmailRegistrationToken token) {
        emailRegistrationTokenRepository.save(token);
    }

    @Override
    public EmailRegistrationToken findByToken(String token) {
        return emailRegistrationTokenRepository.findByToken(token);
    }

    @Override
    public void removeToken(EmailRegistrationToken token) {
        emailRegistrationTokenRepository.delete(token);
    }

    @Override
    public void removeTokenByToken(String token) {
        emailRegistrationTokenRepository.removeByToken(token);
    }

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }
}
