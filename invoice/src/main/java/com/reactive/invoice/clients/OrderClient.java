package com.reactive.invoice.clients;

import com.reactive.invoice.dto.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OrderClient {

  private final WebClient webClient;

  private static final String ORDER_URI = "http://localhost:9090/api/v1/orders/";

  public OrderClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Order> getOrderById(String id) {

    return webClient.get()
        .uri(ORDER_URI + id)
        .retrieve()
        .bodyToMono(Order.class);
  }

}
