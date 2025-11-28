package com.tasteandtwist.controller;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tasteandtwist.model.Cart;
import com.tasteandtwist.model.CartItem;
import com.tasteandtwist.service.OrderService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${app.base.url}")
    private String baseUrl;

    private final OrderService orderService;

    public CheckoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
        System.out.println("Stripe Loaded Successfully!");
    }

    // Show checkout page
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session) {
        return "checkout";
    }

    // Create Stripe checkout session
    @PostMapping("/create-checkout-session")
    @ResponseBody
    public String createCheckoutSession(HttpSession session) throws Exception {

        Cart cart = (Cart) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            throw new Exception("Cart is empty");
        }

        // Convert cart items → stripe line items
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (CartItem ci : cart.getItems()) {

            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(ci.getName())
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("inr")
                            .setUnitAmount(ci.getPrice().multiply(new java.math.BigDecimal(100)).longValue())
                            .setProductData(productData)
                            .build();

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setPriceData(priceData)
                            .setQuantity((long) ci.getQuantity())
                            .build();

            lineItems.add(lineItem);
        }

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .addAllLineItem(lineItems)
                        .setSuccessUrl(baseUrl + "/payment-success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(baseUrl + "/payment-failed")
                        .build();

        Session checkoutSession = Session.create(params);

        return checkoutSession.getUrl();
    }

    // Payment success
    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam("session_id") String sessionId, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) return "redirect:/menus";

        orderService.placeOrderFromCart(cart, "Test User", "9999999999", "Test Address", sessionId);

        session.removeAttribute("CART");

        return "success";
    }

    @GetMapping("/payment-failed")
    public String paymentFailed() {
        return "failed";
    }
}
