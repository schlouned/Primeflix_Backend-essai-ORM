package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Set<MovieEntity> findMovieEntitiesByActorsIn(Set<ActorEntity> actors);
    Set<MovieEntity> findMovieEntitiesByProductorIn(Set<ProductorEntity> productors);
    Set<MovieEntity> findMovieEntitiesByTitleEnLikeIgnoreCase(String title);
    Set<MovieEntity> findMovieEntitiesByTitleFrLikeIgnoreCase(String title);
    List<MovieEntity> findMovieEntitiesByTitleEnContaining(String title);
    List<MovieEntity> findMovieEntitiesByTitleFrContaining(String title);
    List<MovieEntity> findMovieEntitiesByCategoryIs(CategoryEntity category);
    MovieEntity findMovieEntitiesById(Long id);
}
