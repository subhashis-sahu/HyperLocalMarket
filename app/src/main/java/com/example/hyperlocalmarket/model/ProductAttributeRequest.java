package com.example.hyperlocalmarket.model;

import java.util.List;

public class ProductAttributeRequest {

    private Long attributeId;

    private String value;

    private Long optionId;

    private List<Long> optionIds;

    public ProductAttributeRequest() {
    }

    public ProductAttributeRequest(
            Long attributeId,
            String value,
            Long optionId,
            List<Long> optionIds
    ) {
        this.attributeId = attributeId;
        this.value = value;
        this.optionId = optionId;
        this.optionIds = optionIds;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public List<Long> getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(List<Long> optionIds) {
        this.optionIds = optionIds;
    }
}