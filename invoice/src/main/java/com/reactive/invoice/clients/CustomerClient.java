package com.reactive.invoice.clients;

import com.reactive.invoice.dto.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerClient {

  private final WebClient webClient;

  private static final String CUSTOMER_URI = "http://localhost:9090/api/v1/customers/";

  public CustomerClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Customer> getCustomerById(String id) {

    return webClient.get()
        .uri(CUSTOMER_URI + id)
        .retrieve()
        .bodyToMono(Customer.class);
  }

}
