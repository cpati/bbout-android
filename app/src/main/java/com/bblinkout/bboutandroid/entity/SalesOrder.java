package com.bblinkout.bboutandroid.entity;

import java.util.List;

public class SalesOrder {
    private Long orderId;
    private List products;
    private String orderTotal;

    public SalesOrder() {

    }

    public SalesOrder(Long orderId, List products, String orderTotal) {
        this.orderId = orderId;
        this.products = products;
        this.orderTotal = orderTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List getProducts() {
        return products;
    }

    public void setProducts(List products) {
        this.products = products;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
