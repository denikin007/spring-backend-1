package com.sales.market.dto;

import com.sales.market.model.ItemInstance;

public class ItemInstanceDto extends DtoBase<ItemInstance> {
    private Long item;
    private String identifier;// sku

    private Boolean featured = Boolean.FALSE;

    // todo generalmente se usa BigDecimal
    private Double price;

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
