package com.BackPrimeflix.util;

import com.BackPrimeflix.model.UserEntity;

public class Validator {
    //check if there is email and password for user account creation
    public static boolean isUserEmpty(UserEntity userEntity){
        if(userEntity.getPassword() == null || userEntity.getPassword() == ""){
            return true;
        }
        if(userEntity.getEmail() == null || userEntity.getEmail() == ""){
            return true;
        }
        return false;
    }

    //check if there is user informations
    public static boolean isUserInformationEmpty(UserEntity userEntity){
        if(userEntity.getLastName() == null || userEntity.getLastName() == ""){
            return true;
        }
        if(userEntity.getFirstName() == null || userEntity.getFirstName() == ""){
            return true;
        }
        if(userEntity.getAddressEntity() == null){
            return true;
        }
        return false;
    }


}
