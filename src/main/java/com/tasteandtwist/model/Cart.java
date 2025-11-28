package com.tasteandtwist.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Cart implements Serializable {
    // key = menuId
    private Map<Long, CartItem> items = new LinkedHashMap<>();

    public Cart() {}

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public void addItem(CartItem item) {
        CartItem existing = items.get(item.getMenuId());
        if (existing == null) {
            items.put(item.getMenuId(), item);
        } else {
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        }
    }

    public void updateQuantity(Long menuId, int newQty) {
        CartItem existing = items.get(menuId);
        if (existing != null) {
            if (newQty <= 0) {
                items.remove(menuId);
            } else {
                existing.setQuantity(newQty);
            }
        }
    }

    public void removeItem(Long menuId) {
        items.remove(menuId);
    }

    public void clear() {
        items.clear();
    }

    public java.math.BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem it : items.values()) {
            total = total.add(it.getSubTotal());
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
