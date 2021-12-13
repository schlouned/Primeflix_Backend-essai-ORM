package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.OrderStateEntity;
import com.BackPrimeflix.repository.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderStateService")
public class ImplOrderStateService implements OrderStateService{
    //members
    @Autowired
    private OrderStateRepository orderStateRepository;

    public OrderStateEntity findOrderStateEntityByCode(String code){
        return orderStateRepository.findOrderStateEntityByCode(code);
    }
}
