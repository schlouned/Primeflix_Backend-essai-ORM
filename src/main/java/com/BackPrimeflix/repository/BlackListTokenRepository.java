package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    BlackListToken findByToken(String token);
}
