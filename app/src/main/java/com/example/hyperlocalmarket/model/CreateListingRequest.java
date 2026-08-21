package com.example.hyperlocalmarket.model;

import java.math.BigDecimal;
import java.util.List;

public class CreateListingRequest {

    private Long categoryId;
    private String title;
    private String description;
    private List<ProductAttributeRequest> attributes;
    private List<String> imageUrls;
    private BigDecimal price;
    private String condition;
    private String listingType;

    public CreateListingRequest() {
    }

    public CreateListingRequest(
            Long categoryId,
            String title,
            String description,
            List<ProductAttributeRequest> attributes,
            List<String> imageUrls,
            BigDecimal price,
            String condition,
            String listingType
    ) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.attributes = attributes;
        this.imageUrls = imageUrls;
        this.price = price;
        this.condition = condition;
        this.listingType = listingType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public List<ProductAttributeRequest> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttributeRequest> attributes) {
        this.attributes = attributes;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }
}