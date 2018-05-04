package com.bblinkout.bboutandroid.entity;

import java.io.Serializable;
import java.util.List;

public class SalesOrder implements Serializable{
    private Long orderId;
    private Long userId;
    private List<Product> products;
    private String orderTotal;
    private String totalProducts;
    private String orderDate;

    public SalesOrder() {

    }

    public SalesOrder(Long orderId, Long userId, List<Product> products, String orderTotal) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.orderTotal = orderTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(String totalProducts) {
        this.totalProducts = totalProducts;
    }
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

}
