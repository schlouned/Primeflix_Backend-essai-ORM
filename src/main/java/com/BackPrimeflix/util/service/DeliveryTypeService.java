package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.DeliveryTypeEntity;

public interface DeliveryTypeService {
    DeliveryTypeEntity findDeliveryTypeEntityByCode(String code);
}
