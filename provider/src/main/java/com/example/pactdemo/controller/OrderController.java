package com.example.pactdemo.controller;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pactdemo.model.Order;
import com.example.pactdemo.store.OrderStore;


@RestController
public class OrderController {
    private static final OrderStore orderStore = new OrderStore();

    @GetMapping("/order")
    public ResponseEntity<?> orders() {
        return ResponseEntity.ok().body(orderStore.getOrders());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> order(@PathVariable("id") @NotBlank(message = "Order ID cannot be empty") String id) {
		Optional<Order> maybeOrder = orderStore.getOrder(id);
		if (maybeOrder.isEmpty()) {
		    return ResponseEntity.status(404).body("{\"error\": \"Order not found\"}");
        }

		return ResponseEntity.ok().body(maybeOrder.get());
	}

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") @NotBlank(message = "Order ID cannot be empty") String id) {
        orderStore.deleteOrder(id);
        return ResponseEntity.ok().body("Order deleted");
    }

    @PostMapping("/order")
    public ResponseEntity<?> addOrder(Order order) {
        orderStore.addOrder(order);
        return ResponseEntity.ok().body("Order added");
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") @NotBlank(message = "Order ID cannot be empty") String id, Order order) {
        orderStore.updateOrder(id, order);
        return ResponseEntity.ok().body("Order updated");
    }
}
