package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.EmailRegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//access to the DB = DAO
@Repository
public interface EmailRegistrationTokenRepository extends JpaRepository<EmailRegistrationToken, Long > {
    EmailRegistrationToken findByToken(final String token);
    Long removeByToken(String token);
}
