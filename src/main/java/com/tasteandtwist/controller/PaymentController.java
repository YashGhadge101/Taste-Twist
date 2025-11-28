package com.tasteandtwist.controller;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.tasteandtwist.model.Cart;
import com.tasteandtwist.service.CartService;
import com.tasteandtwist.service.OrderService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {

    private final CartService cartService;
    private final OrderService orderService;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(String session_id, HttpSession httpSession, Model model) throws Exception {

        // Basic safety check
        String expected = (String) httpSession.getAttribute("stripeSessionId");

        // Retrieve Stripe session
        Session session = Session.retrieve(session_id);

        // Validate payment status
        if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {

            // Fetch cart + customer details
            Cart cart = cartService.getCart(httpSession);

            String name = (String) httpSession.getAttribute("checkoutCustomerName");
            String phone = (String) httpSession.getAttribute("checkoutPhone");
            String address = (String) httpSession.getAttribute("checkoutAddress");

            // Save order in database
            orderService.placeOrderFromCart(cart, name, phone, address, session_id);

            // Clear cart after successful order
            cartService.clearCart(httpSession);

            model.addAttribute("sessionId", session_id);
            return "payment-success";

        } else {
            model.addAttribute("reason", "Payment status: " + session.getPaymentStatus());
            return "payment-failed";
        }
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(Model model) {
        return "payment-cancel";
    }
}
