package com.BackPrimeflix.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("P")
public class ProductorEntity extends PersonEntity implements Serializable {
    @OneToMany( targetEntity=MovieEntity.class, mappedBy= "productor")
    private Set<MovieEntity> movies = new HashSet<>();
}
