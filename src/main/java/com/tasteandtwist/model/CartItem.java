package com.tasteandtwist.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    private Long menuId;
    private String name;
    private BigDecimal price;
    private int quantity;

    public CartItem() {}

    public CartItem(Long menuId, String name, BigDecimal price, int quantity) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getMenuId() { return menuId; }
    public void setMenuId(Long menuId) { this.menuId = menuId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public java.math.BigDecimal getSubTotal() {
        return price.multiply(new BigDecimal(quantity));
    }
}
