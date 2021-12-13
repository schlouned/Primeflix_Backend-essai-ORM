package com.BackPrimeflix.repository;
import com.BackPrimeflix.model.OrderEntity;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findOrderEntityById(Long id);
    List<OrderEntity> findAllByOrderByIdAsc();

}
