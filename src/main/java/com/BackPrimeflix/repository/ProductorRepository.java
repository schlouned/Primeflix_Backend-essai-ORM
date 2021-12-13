package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.ProductorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductorRepository extends JpaRepository<ProductorEntity, Long> {
    Set<ProductorEntity> findProductorEntityByLastNameLikeIgnoreCase(String lastName);
    Set<ProductorEntity> findProductorEntityByFirstNameLikeIgnoreCase(String firstName);
}
