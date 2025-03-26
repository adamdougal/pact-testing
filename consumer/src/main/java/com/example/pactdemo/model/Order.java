package com.example.pactdemo.model;

import java.time.LocalDateTime;
import java.util.List;

public record Order(String id, User user, List<Item> items, double totalPrice, LocalDateTime orderDate) {

}