package com.bblinkout.bboutandroid.entity;

/**
 * Created by chidanandapati on 4/22/18.
 */

public class CartItem {
    private String name;
    private String description;
    private Double price;
    private String UOM;
    private String barCode;

    public CartItem() {
    }

    public CartItem(String name, String description, Double price, String UOM, String barCode) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.UOM = UOM;
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
