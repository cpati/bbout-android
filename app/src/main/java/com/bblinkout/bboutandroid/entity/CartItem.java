package com.bblinkout.bboutandroid.entity;

/**
 * Created by chidanandapati on 4/22/18.
 */

public class CartItem {
    private String productName;
    private String productDescription;
    private Double price;
    private String UOM;

    public CartItem() {
    }

    private String barCode;


    public CartItem(String productName, String productDescription, Double price, String UOM) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.UOM = UOM;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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
