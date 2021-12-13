package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.ActorEntity;
import com.BackPrimeflix.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    Set<ActorEntity> findActorEntityByLastNameLikeIgnoreCase(String lastName);
    Set<ActorEntity> findActorEntityByFirstNameLikeIgnoreCase(String firstName);
}
