package com.tasteandtwist.service;

import com.tasteandtwist.model.Cart;
import com.tasteandtwist.model.CartItem;
import com.tasteandtwist.model.Order;
import com.tasteandtwist.model.OrderItem;
import com.tasteandtwist.repository.OrderRepository;
import com.tasteandtwist.repository.OrderItemRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // ---------------- CREATE ORDER FROM CART ----------------
    public void placeOrderFromCart(Cart cart, String customerName, String phone,
                                   String address, String paymentSessionId) {

        if (cart == null || cart.isEmpty()) return;

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setTotalAmount(cart.getTotal());
        order.setPaymentSessionId(paymentSessionId);
        order.setStatus("PAID");
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        for (CartItem it : cart.getItems()) {
            OrderItem item = new OrderItem(
                    order,
                    it.getMenuId(),
                    it.getName(),
                    it.getPrice(),
                    it.getQuantity(),
                    it.getSubTotal()
            );
            orderItemRepository.save(item);
        }
    }

    // ---------------- UPDATE ORDER STATUS ----------------
    public Order updateStatus(Long orderId, String newStatus) {

        if (orderId == null || newStatus == null) return null;

        String status = newStatus.trim().toUpperCase(Locale.ROOT);

        List<String> allowed = Arrays.asList(
                "PENDING", "PAID", "PREPARING", "READY", "DELIVERED", "CANCELLED", "CONFIRMED"
        );

        if (!allowed.contains(status)) return null;

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;

        order.setStatus(status);
        return orderRepository.save(order);
    }

    // ---------------- BASIC CRUD ----------------
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
