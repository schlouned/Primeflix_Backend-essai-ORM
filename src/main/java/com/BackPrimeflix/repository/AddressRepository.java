package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.ActorEntity;
import com.BackPrimeflix.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
