package com.BackPrimeflix.util.service;

import com.BackPrimeflix.context.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(final AbstractEmailContext email) throws MessagingException;
}
