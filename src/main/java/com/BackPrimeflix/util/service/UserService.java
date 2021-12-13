package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.UserDto;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.exception.InvalidTokenException;
import com.BackPrimeflix.exception.UnkownIdentifierException;
import com.BackPrimeflix.exception.UserAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity register(final UserEntity user, String registrationMode) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final UserEntity user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    UserEntity getUserById(final String id) throws UnkownIdentifierException;
    UserEntity getByEmail(String email);
    void save(final UserEntity userEntity);
    Integer checkCart(UserEntity userEntity);
    Boolean checkUserRoleAllowed(UserEntity userEntity, String roleLevel);
    UserDto convertToUserDto(UserEntity userEntity);
    UserEntity convertToUserEntity(UserDto userDto);
    UserEntity saveUser(UserDto userDto);
    List<UserEntity> getUsersList();
    void deleteUser(Long id);
}
