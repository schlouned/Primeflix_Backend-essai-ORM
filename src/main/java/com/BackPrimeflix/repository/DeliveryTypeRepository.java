package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.DeliveryTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryTypeEntity, Long> {
    DeliveryTypeEntity findDeliveryTypeEntityByCode(String code);
}
