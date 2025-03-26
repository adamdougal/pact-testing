package com.example.pactdemo.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.example.pactdemo.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderClient {

    private final String host;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public OrderClient(String host) {
        this.host = host;
        this.httpClient = HttpClient.newBuilder().build();
        this.mapper = new ObjectMapper();
    }

    public Order getOrder(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(host + "/order/" + id))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get order: " + response.body());
        }

        String responseBody = response.body();
        return mapper.readValue(responseBody, Order.class);
    }
}
