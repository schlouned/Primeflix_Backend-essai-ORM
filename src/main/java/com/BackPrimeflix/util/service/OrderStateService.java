package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.OrderStateEntity;

public interface OrderStateService {
    OrderStateEntity findOrderStateEntityByCode(String code);
}
