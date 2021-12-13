package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
public class MovieEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titleEn;
    private String titleFr;
    private Long releaseYear;
    @ManyToOne
    @JoinColumn( name="productor_id", nullable = false)
    private ProductorEntity productor;
    @ManyToMany(mappedBy = "movies")
    private Set<ActorEntity> actors = new HashSet<>();
    @ManyToOne
    @JoinColumn( name="category_id", nullable = false)
    private CategoryEntity category;
    private String pictureUrl;
    @Column(columnDefinition="TEXT")
    private String summaryEn;
    @Column(columnDefinition="TEXT")
    private String summaryFr;
    private Integer duration;
    private Float price;
    private Integer stockQuantity;
    @OneToMany( targetEntity=CartItemEntity.class, mappedBy= "movie")
    private Set<CartItemEntity> cartItemEntities = new HashSet<>();
    @OneToMany( targetEntity=InventoryEntity.class, mappedBy= "movie")
    private Set<InventoryEntity> inventoryEntities = new HashSet<>();

    //constructor
    public MovieEntity() {
    }

    //getters setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String title) {
        this.titleEn = title;
    }

    public Long getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ProductorEntity getProductor() {
        return productor;
    }

    public void setProductor(ProductorEntity productor) {
        this.productor = productor;
    }

    public Set<ActorEntity> getActors() {
        return actors;
    }

    public void setActors(Set<ActorEntity> actors) {
        this.actors = actors;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitleFr() {return titleFr;}

    public void setTitleFr(String titleFr) {this.titleFr = titleFr;}

    public String getSummaryEn() {return summaryEn;}

    public void setSummaryEn(String summaryEn) {this.summaryEn = summaryEn;}

    public String getSummaryFr() {return summaryFr;}

    public void setSummaryFr(String summaryFr) {this.summaryFr = summaryFr;}

    public Integer getDuration() {return duration;}

    public void setDuration(Integer duration) {this.duration = duration;}

    public Float getPrice() {return price;}

    public void setPrice(Float price) {this.price = price;}

    public Integer getStockQuantity() {return stockQuantity;}

    public void setStockQuantity(Integer stockQuantity) {this.stockQuantity = stockQuantity;}

    public Set<CartItemEntity> getCartItemEntities() {return cartItemEntities;}

    public void setCartItemEntities(Set<CartItemEntity> cartItemEntities) {this.cartItemEntities = cartItemEntities;}

    public Set<InventoryEntity> getInventoryEntities() {
        return inventoryEntities;
    }

    public void setInventoryEntities(Set<InventoryEntity> inventoryEntities) {
        this.inventoryEntities = inventoryEntities;
    }
}
