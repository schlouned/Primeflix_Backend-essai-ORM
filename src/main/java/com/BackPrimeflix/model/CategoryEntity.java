package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CategoryEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany( targetEntity=MovieEntity.class, mappedBy= "category")
    private Set<MovieEntity> movies = new HashSet<>();
    @OneToMany( targetEntity=DiscountEntity.class, mappedBy= "category")
    private Set<DiscountEntity> discounts = new HashSet<>();

    //constructor
    public CategoryEntity() {
    }

    //getters setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MovieEntity> getMovies() {return movies;}

    public void setMovies(Set<MovieEntity> movies) {this.movies = movies;}

    public Set<DiscountEntity> getDiscounts() {return discounts;}

    public void setDiscounts(Set<DiscountEntity> discounts) {this.discounts = discounts;}

    //methods
    public DiscountEntity getCurrentDiscount(){
        //members
        Date date = new Date();
        Set<DiscountEntity> discounts = this.getDiscounts();
        DiscountEntity discountEntity = new DiscountEntity();
        //recover discount list
        for(DiscountEntity discount: discounts) {
            if (discount.getStartDate().before(date) && discount.getEndDate().after(date)) {
                discountEntity = discount;
                break;
            }
        }
        //return
        return discountEntity;
    }

}
