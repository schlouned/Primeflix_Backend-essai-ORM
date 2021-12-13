package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.DeliveryTypeEntity;
import com.BackPrimeflix.repository.DeliveryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deliveryTypeService")
public class ImplDeliveryTypeService implements DeliveryTypeService{
    //members
    @Autowired
    DeliveryTypeRepository deliveryTypeRepository;

    //methods
    public DeliveryTypeEntity findDeliveryTypeEntityByCode(String code){
        return  deliveryTypeRepository.findDeliveryTypeEntityByCode(code);
    }
}
