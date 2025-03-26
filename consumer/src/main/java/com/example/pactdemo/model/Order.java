package com.example.pactdemo.model;

import java.util.Date;
import java.util.List;

public record Order(String id, User user, List<Item> items, double totalPrice, Date orderDate) {

}