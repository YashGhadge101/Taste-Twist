package com.tasteandtwist.controller;

import com.tasteandtwist.model.Cart;
import com.tasteandtwist.model.Menu;
import com.tasteandtwist.repository.MenuRepository;
import com.tasteandtwist.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MenuRepository menuRepository;

    // View cart page
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "cart";
    }

    // ------------------------------
    // GET ADD-TO-CART (requested)
    // ------------------------------
    @GetMapping("/add/{menuId}")
    public String addToCartGet(@PathVariable Long menuId, HttpSession session) {

        Optional<Menu> maybeMenu = menuRepository.findById(menuId);
        if (maybeMenu.isEmpty()) {
            return "redirect:/menus";
        }

        Menu menu = maybeMenu.get();

        // Add with quantity = 1
        cartService.addToCart(
                session,
                menu.getId(),
                menu.getName(),
                menu.getPrice(),      // BigDecimal
                1
        );

        return "redirect:/cart";
    }

    // ------------------------------
    // POST ADD-TO-CART (form button)
    // ------------------------------
    @PostMapping("/add/{menuId}")
    public String addToCartPost(
            @PathVariable Long menuId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session) {

        Optional<Menu> maybeMenu = menuRepository.findById(menuId);
        if (maybeMenu.isEmpty()) {
            return "redirect:/menus";
        }

        Menu menu = maybeMenu.get();

        cartService.addToCart(
                session,
                menu.getId(),
                menu.getName(),
                menu.getPrice(),     // BigDecimal
                quantity
        );

        return "redirect:/cart";
    }

    // Update quantity
    @PostMapping("/update")
    public String updateQuantity(
            @RequestParam Long menuId,
            @RequestParam int quantity,
            HttpSession session) {

        cartService.updateQuantity(session, menuId, quantity);
        return "redirect:/cart";
    }

    // Remove item
    @PostMapping("/remove/{menuId}")
    public String removeItem(@PathVariable Long menuId, HttpSession session) {
        cartService.removeItem(session, menuId);
        return "redirect:/cart";
    }

    // Clear cart
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }
}
