package com.BackPrimeflix.util.service;

import com.BackPrimeflix.model.AddressEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addressService")
public class ImplAddressService implements AddressService{
    //members
    @Autowired
    AddressRepository addressRepository;

    //methods
    public AddressEntity save(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    public void delete(AddressEntity addressEntity){
        addressRepository.delete(addressEntity);
    }
}
