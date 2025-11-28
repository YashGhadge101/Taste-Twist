package com.tasteandtwist.service;

import com.tasteandtwist.model.Cart;
import com.tasteandtwist.model.CartItem;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;  // ✅ Correct import for Spring Boot 3
import java.math.BigDecimal;

@Service
public class CartService {

    private static final String SESSION_CART_KEY = "CART";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    /**
     * Correct method signature for BigDecimal-based price.
     */
    public void addToCart(HttpSession session,
                          Long menuId,
                          String name,
                          BigDecimal price,
                          int quantity) {

        Cart cart = getCart(session);

        CartItem item = new CartItem(
                menuId,
                name,
                price,
                quantity
        );

        cart.addItem(item);
    }

    public void updateQuantity(HttpSession session, Long menuId, int quantity) {
        Cart cart = getCart(session);
        cart.updateQuantity(menuId, quantity);
    }

    public void removeItem(HttpSession session, Long menuId) {
        Cart cart = getCart(session);
        cart.removeItem(menuId);
    }

    public void clearCart(HttpSession session) {
        Cart cart = getCart(session);
        cart.clear();
    }
}
