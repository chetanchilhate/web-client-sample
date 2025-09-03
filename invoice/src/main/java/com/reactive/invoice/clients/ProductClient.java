package com.reactive.invoice.clients;

import static com.reactive.invoice.clients.util.ClientUtil.ECOM_API_URL;

import com.reactive.invoice.dto.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

  private final WebClient webClient;

  private static final String PRODUCT_URI = ECOM_API_URL + "/v1/products/";

  public ProductClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Product> getProductById(int id) {

    return webClient.get()
        .uri(PRODUCT_URI + id)
        .retrieve()
        .bodyToMono(Product.class);
  }

}
