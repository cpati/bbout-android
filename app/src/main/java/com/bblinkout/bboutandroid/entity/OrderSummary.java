package com.bblinkout.bboutandroid.entity;

public class OrderSummary {
    private Double orderSubTotal;
    private Double orderTax;
    private Double orderTotal;

    public OrderSummary(Double orderSubTotal, Double orderTax, Double orderTotal) {
        this.orderSubTotal = orderSubTotal;
        this.orderTax = orderTax;
        this.orderTotal = orderTotal;
    }

    public Double getOrderSubTotal() {
        return orderSubTotal;
    }

    public void setOrderSubTotal(Double orderSubTotal) {
        this.orderSubTotal = orderSubTotal;
    }

    public Double getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(Double orderTax) {
        this.orderTax = orderTax;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }
}
