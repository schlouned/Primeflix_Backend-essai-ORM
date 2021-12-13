package com.BackPrimeflix.context;

import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.model.UserEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerifEmailContext extends AbstractEmailContext {
    //member
    private String token;

    @Override
    public <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        UserEntity client = (UserEntity) context; // we pass the client information
        put("firstName", client.getFirstName());
        setTemplateLocation("templates/emails/email-verification");
        setSubject("Complete your registration");
        setFrom(WebConstants.EmailDenis);
        setTo(client.getEmail());
    }

    //set the token
    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    //method to build the url link to do the account verification
    //the path is the link to the rest service
    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path(WebConstants.EmailVerificationUrl).queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
