package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.CartItemEntity;
import com.BackPrimeflix.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    Set<CartItemEntity> findCartItemEntitiesByCartOrderByMovie(CartEntity cartEntity);

    CartItemEntity findCartItemEntitiesById(Long id);
    void deleteCartItemEntityById(Long id);

    @Override
    void delete(CartItemEntity entity);

    CartItemEntity findCartItemEntityByMovieAndCart(MovieEntity movie, CartEntity cart);
}
