package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.ActorEntity;
import com.BackPrimeflix.model.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
    DiscountEntity getDiscountEntityById(Long id);
}
