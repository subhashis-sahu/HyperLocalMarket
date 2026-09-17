package com.example.hyperlocalmarket.model;

import java.math.BigDecimal;

public class ProductInfo {

    private Long id;
    private String title;
    private String description;
    private String condition;
    private String status;
    private BigDecimal price;
    private String imageUrl;
    private String location;
    private SellerInfoDto seller;

    public ProductInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SellerInfoDto getSeller() {
        return seller;
    }

    public void setSeller(SellerInfoDto seller) {
        this.seller = seller;
    }
}
