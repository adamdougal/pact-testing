package com.example.pactdemo.store;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.pactdemo.model.Item;
import com.example.pactdemo.model.Order;
import com.example.pactdemo.model.User;

public class OrderStore {

    private static final Map<String, Order> orders = new HashMap<>();
    
    public OrderStore() {
        User martyUser = new User("881985", "Marty", "McFly", "McFlyinTime@futurebound.com");
        List<Item> martyItems = List.of(
            new Item("1", "Hoverboard Revamp Kit", "Turn your trusty hoverboard into a sleek, futuristic ride. This upgrade kit comes complete with LED accents and aerodynamic enhancements—perfect for zipping through time and space.", 159.99), 
            new Item("2", "Time-Traveling Sneaker Laces", "Infuse your kicks with retro-futuristic flair! These self-adjusting, luminous sneaker laces are crafted for high-speed adventures, ensuring your style is always ahead of its time.", 24.99)
        );
        Order martyOrder = new Order("88", martyUser, martyItems, 184.98, LocalDateTime.of(2015, 10, 21, 10, 30));
        orders.put(martyOrder.id(), martyOrder);

        User docUser = new User("1211985", "Doc", "Brown", "GreatScott@timecircuit.com");
        List<Item> docItems = List.of(
            new Item("1", "Temporal Flux Stabilizer", "This state-of-the-art device keeps your time circuits in perfect sync—no more pesky temporal anomalies during your experiments!", 499.99),
            new Item("2", "Lightning Capture Harness", "Safely snag the power of a lightning strike with this innovative contraption, engineered to supercharge your time-travel experiments.", 349.99),
            new Item("3", "Gadget Guru Lab Coat", "Combine style with functionality in the lab. This coat comes with built-in LED accents and plenty of pockets for all your inventive gadgets.", 89.99)
        );
        Order docOrder = new Order("121", docUser, docItems, 939.97, LocalDateTime.of(1955, 11, 12, 14, 45));
        orders.put(docOrder.id(), docOrder);

        User biffUser = new User("24331985", "Biff", "Tannen", "BigBiff@bullymail.com");
        List<Item> biffItems = List.of(
            new Item("1", "Brute Force Boxing Gloves", "Deliver knockout punches with these rugged gloves designed to exude raw power—perfect for any bully's arsenal.", 69.99),
            new Item("2", "Turbo Truck", "Built for dominance, this high-performance truck offers rugged style and serious horsepower—ideal for hauling cash and making an entrance.", 42000.00),
            new Item("3", "Money Bags Deluxe Briefcase", "Flaunt your wealth with this premium briefcase, crafted to carry cash, contracts, and a few secret bully tricks.", 199.99)
        );
        Order biffOrder = new Order("B01", biffUser, biffItems, 42269.98, LocalDateTime.of(1985, 10, 21, 16, 0));
        orders.put(biffOrder.id(), biffOrder);
    }

    public Optional<Order> getOrder(String id) {
        if (orders.containsKey(id)) {
            return Optional.of(orders.get(id));
        } 
        
        return Optional.empty();
    }

    public List<Order> getOrders() {
        return orders.values().stream().toList();
    }

    public void addOrder(Order order) {
        orders.put(order.id(), order);
    }

    public void deleteOrder(String id) {
        orders.remove(id);
    }

    public void updateOrder(String id, Order order) {
        if (orders.containsKey(id)) {
            orders.put(id, order);
        }
    }

}
