package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("A")
public class ActorEntity extends PersonEntity implements Serializable {
    //members
    @ManyToMany
    @JoinTable( name = "actor_movie_association",
            joinColumns = @JoinColumn( name = "actor_id" ),
            inverseJoinColumns = @JoinColumn( name = "movie_id" ) )
    private Set<MovieEntity> movies = new HashSet<>();

    //constructor
    public ActorEntity() {
    }

    //getters setters
    public Set<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieEntity> movies) {
        this.movies = movies;
    }
}
