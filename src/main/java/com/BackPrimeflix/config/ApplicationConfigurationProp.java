package com.BackPrimeflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfigurationProp extends AbstractSecurityWebApplicationInitializer {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
