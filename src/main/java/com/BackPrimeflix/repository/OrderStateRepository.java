package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.ActorEntity;
import com.BackPrimeflix.model.OrderEntity;
import com.BackPrimeflix.model.OrderStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderStateEntity, Long> {
    OrderStateEntity findOrderStateEntityByCode(String code);
}
