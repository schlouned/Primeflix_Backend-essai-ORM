package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.OrderDto;
import com.BackPrimeflix.model.OrderEntity;
import com.BackPrimeflix.model.UserEntity;

import java.util.List;

public interface OrderService {
    String createGPSCalculationUrl(String emailUser);
    Float calculateDeliveryCost(String email);
    Float calculateCartCost(UserEntity userEntity);
    OrderEntity save(OrderEntity orderEntity);
    OrderEntity findOrderEntityById(Long id);
    OrderDto convertToOrderDto(OrderEntity orderEntity);
    void delete(OrderEntity orderEntity);
    List<OrderEntity> findOrderByUser(UserEntity userEntity);
    List<OrderEntity> findAllOrders();
    void orderNextStatus(OrderEntity order);
}
