package com.bblinkout.bboutandroid.entity;

import java.io.Serializable;

public class Product implements Serializable{
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int Quantity;
    private String barCode;
    private String imageBlob;


    public Product(Long id, String name, String description, Double price, int quantity, String barCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        Quantity = quantity;
        this.barCode = barCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(String imageBlob) {
        this.imageBlob = imageBlob;
    }
}
