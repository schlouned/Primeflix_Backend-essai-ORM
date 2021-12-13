package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.ActorEntity;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

}
