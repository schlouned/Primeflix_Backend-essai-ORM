package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class InventoryEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private MovieEntity movie;
    private Date inventoryDate;
    private Integer stockBeforeInventory;
    private Integer stockAfterInventory;
    private Integer difference;

    //constructor
    public InventoryEntity() {
    }

    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public Integer getStockBeforeInventory() {
        return stockBeforeInventory;
    }

    public void setStockBeforeInventory(Integer stockBeforeInventory) {
        this.stockBeforeInventory = stockBeforeInventory;
    }

    public Integer getStockAfterInventory() {
        return stockAfterInventory;
    }

    public void setStockAfterInventory(Integer stockAfterInventory) {
        this.stockAfterInventory = stockAfterInventory;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }
}
