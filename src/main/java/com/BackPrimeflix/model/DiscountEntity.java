package com.BackPrimeflix.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DiscountEntity implements Serializable {
    //members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer percentage;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn( name="category_id", nullable = false)
    private CategoryEntity category;

    //constructor
    public DiscountEntity() {}

    //getters and setters
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Integer getPercentage() {return percentage;}

    public void setPercentage(Integer percentage) {this.percentage = percentage;}

    public Date getStartDate() {return startDate;}

    public void setStartDate(Date startDate) {this.startDate = startDate;}

    public Date getEndDate() {return endDate;}

    public void setEndDate(Date endDate) {this.endDate = endDate;}

    public CategoryEntity getCategory() {return category;}

    public void setCategory(CategoryEntity category) {this.category = category;}

}
