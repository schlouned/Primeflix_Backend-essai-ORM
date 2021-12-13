package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.AddressEntity;

public interface AddressService {
    AddressEntity save(AddressEntity addressEntity);
    void delete(AddressEntity addressEntity);
}
