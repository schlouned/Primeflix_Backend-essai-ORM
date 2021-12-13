package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    List<UserEntity> findAllByOrderByLastName();
}
