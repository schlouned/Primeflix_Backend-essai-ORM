package com.BackPrimeflix.response;

import com.BackPrimeflix.dto.OrderDto;

import java.util.List;

public class OrderResponse extends Response{
    //members
    OrderDto orderDto;
    List<OrderDto> orders;

    //getters and setters
    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
