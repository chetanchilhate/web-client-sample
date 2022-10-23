package com.reactive.invoice.clients;

import static com.reactive.invoice.clients.util.ClientUtil.ECOM_API_URL;

import com.reactive.invoice.clients.util.ClientUtil;
import com.reactive.invoice.dto.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerClient {

  private final WebClient webClient;

  private static final String CUSTOMER_URI = ECOM_API_URL + "/v1/customers/";

  public CustomerClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Customer> getCustomerById(String id) {

    return webClient.get()
        .uri(CUSTOMER_URI + id)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, this::handleClientError)
        .onStatus(HttpStatus::is5xxServerError, this::handleClientError)
        .bodyToMono(Customer.class);
  }

  private Mono<? extends Throwable> handleClientError(ClientResponse response) {
    return ClientUtil.handleError(response, "CUSTOMER_SERVICE_FAILURE client error ");
  }

}
