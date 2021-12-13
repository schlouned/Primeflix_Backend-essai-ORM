package com.BackPrimeflix.dto;

import java.io.Serializable;
import java.util.List;

public class MovieDto implements Serializable {
    //members
    private String id;
    private String titleEn;
    private String titlefr;
    private ProductorDto productor;
    private List<ActorDto> actors;
    private String releaseYear;
    private CategoryDto category;
    private String pictureUrl;
    private String summaryEn;
    private String summaryFr;
    private String duration;
    private String price;
    private Integer stockQuantity;

    public MovieDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public ProductorDto getProductor() {
        return productor;
    }

    public void setProductor(ProductorDto productor) {
        this.productor = productor;
    }

    public List<ActorDto> getActors() {
        return actors;
    }

    public void setActors(List<ActorDto> actors) {
        this.actors = actors;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitlefr() {return titlefr;}

    public void setTitlefr(String titlefr) {this.titlefr = titlefr;}

    public String getSummaryEn() {return summaryEn;}

    public void setSummaryEn(String summaryEn) {this.summaryEn = summaryEn;}

    public String getSummaryFr() {return summaryFr;}

    public void setSummaryFr(String summaryFr) {this.summaryFr = summaryFr;}

    public String getDuration() {return duration;}

    public void setDuration(String duration) {this.duration = duration;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}

    public Integer getStockQuantity() {return stockQuantity;}

    public void setStockQuantity(Integer stockQuantity) {this.stockQuantity = stockQuantity;}

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

}
