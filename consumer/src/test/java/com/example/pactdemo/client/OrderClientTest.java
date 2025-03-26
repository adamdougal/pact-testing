package com.example.pactdemo.client;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.example.pactdemo.model.Order;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.LambdaDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@PactConsumerTest
@PactDirectory("../pacts")
@PactTestFor(providerName = "PactDemoProvider")
public class OrderClientTest {

      @Pact(provider = "PactDemoProvider", consumer = "PactDemoConsumer")
      public V4Pact orderPact(PactDslWithProvider builder) {
            LambdaDslJsonBody expectedResponseBody = newJsonBody((expectedOrder) -> {
                  expectedOrder.stringType("id", "88");
                  expectedOrder.object("user", (user) -> {
                        user.stringType("id", "881985");
                        user.stringType("firstName", "Marty");
                        user.stringType("lastName", "McFly");
                        user.stringType("email", "McFlyinTime@futurebound.com");
                  });
                  expectedOrder.array("items", (item) -> {
                        item.object((itemObj) -> {
                              itemObj.stringType("id", "1");
                              itemObj.stringType("name", "Hoverboard Revamp Kit");
                              itemObj.stringType("description",
                                          "Turn your trusty hoverboard into a sleek, futuristic ride. This upgrade kit comes complete with LED accents and aerodynamic enhancements—perfect for zipping through time and space.");
                              itemObj.numberType("price", 159.99);
                        });
                        item.object((itemObj) -> {
                              itemObj.stringType("id", "2");
                              itemObj.stringType("name", "Time-Traveling Sneaker Laces");
                              itemObj.stringType("description",
                                          "Infuse your kicks with retro-futuristic flair! These self-adjusting, luminous sneaker laces are crafted for high-speed adventures, ensuring your style is always ahead of its time.");
                              itemObj.numberType("price", 24.99);
                        });
                  });
                  expectedOrder.numberType("totalPrice", 184.98);
                  expectedOrder.date("orderDate", "yyyy-MM-dd'T'HH:mm:ss", Date.from(LocalDateTime.parse("2015-10-21T10:30:00").toInstant(UTC))); 
            });

            return builder
                        .given("order with ID 88 exists")
                        .uponReceiving("a request to get an order by id")
                        .path("/order/88")
                        .method("GET")
                        .headers("Accept", "application/json")
                        .willRespondWith()
                        .status(200)
                        .headers(Map.of("Content-Type", "application/json"))
                        .body(expectedResponseBody.build())
                        .toPact(V4Pact.class);
      }

      @Pact(provider = "PactDemoProvider", consumer = "PactDemoConsumer")
      public V4Pact orderDoesNotExistPact(PactDslWithProvider builder) {

            return builder
                        .given("order with ID 999 does not exist")
                        .uponReceiving("a request to get an order by id that does not exist")
                        .path("/order/999")
                        .method("GET")
                        .headers("Accept", "application/json")
                        .willRespondWith()
                        .status(404)
                        .headers(Map.of("Content-Type", "application/json"))
                        .body("{\"error\": \"Order not found\"}")
                        .toPact(V4Pact.class);
      }

      @Test
      @PactTestFor(pactMethod = "orderPact")
      void testGetOrder(MockServer mockServer) throws Exception {
            OrderClient orderClient = new OrderClient(mockServer.getUrl());

            Order order = orderClient.getOrder("88");

            assertNotNull(order);
      }

      @Test
      @PactTestFor(pactMethod = "orderDoesNotExistPact")
      void testGetOrderThatDoesNotExist(MockServer mockServer) throws Exception {
            OrderClient orderClient = new OrderClient(mockServer.getUrl());

            Throwable thrownException = assertThrows(RuntimeException.class, () -> {
                  orderClient.getOrder("999");
            });
            assertEquals("Failed to get order: {\"error\": \"Order not found\"}", thrownException.getMessage());
      }
}