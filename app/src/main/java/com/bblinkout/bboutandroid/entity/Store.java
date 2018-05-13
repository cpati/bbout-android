package com.bblinkout.bboutandroid.entity;

public class Store {
    private Long id;
    private String storeName;
    private String storeDescription;
    private String storeAddress;
    private Double storeCordinatesLat;
    private Double storeCordinatesLong;

    public Store() {
    }

    public Store(Long id, String storeName, String storeDescription, String storeAddress, Double storeCordinatesLat, Double storeCordinatesLong) {
        this.id = id;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storeAddress = storeAddress;
        this.storeCordinatesLat = storeCordinatesLat;
        this.storeCordinatesLong = storeCordinatesLong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Double getStoreCordinatesLat() {
        return storeCordinatesLat;
    }

    public void setStoreCordinatesLat(Double storeCordinatesLat) {
        this.storeCordinatesLat = storeCordinatesLat;
    }

    public Double getStoreCordinatesLong() {
        return storeCordinatesLong;
    }

    public void setStoreCordinatesLong(Double storeCordinatesLong) {
        this.storeCordinatesLong = storeCordinatesLong;
    }
}
