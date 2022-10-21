package com.reactive.invoice.clients;

import com.reactive.invoice.dto.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductClient {

  private final WebClient webClient;

  private static final String PRODUCT_URI = "http://localhost:9090/api/v1/products/";

  public ProductClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Product getProductById(String id) {

    return webClient.get()
        .uri(PRODUCT_URI + id)
        .retrieve()
        .bodyToMono(Product.class)
        .block();
  }

}
