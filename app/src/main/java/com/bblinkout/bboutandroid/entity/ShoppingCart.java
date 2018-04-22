package com.bblinkout.bboutandroid.entity;

import java.util.List;

public class ShoppingCart {
    private List<CartItem> cartItems;
    private Double orderTotal;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }
}
