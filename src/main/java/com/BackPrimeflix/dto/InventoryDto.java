package com.BackPrimeflix.dto;

import java.io.Serializable;
import java.util.Date;

public class InventoryDto implements Serializable {
    //members
    private String id;
    private String movieId;
    private MovieDto movie;
    private String date;
    private String stockBeforeInventory;
    private String stockAfterInventory;
    private String difference;

    //constructor
    public InventoryDto() {
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStockBeforeInventory() {
        return stockBeforeInventory;
    }

    public void setStockBeforeInventory(String stockBeforeInventory) {
        this.stockBeforeInventory = stockBeforeInventory;
    }

    public String getStockAfterInventory() {
        return stockAfterInventory;
    }

    public void setStockAfterInventory(String stockAfterInventory) {
        this.stockAfterInventory = stockAfterInventory;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }
}
