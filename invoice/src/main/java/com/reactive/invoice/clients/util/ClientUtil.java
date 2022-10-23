package com.reactive.invoice.clients.util;

import com.reactive.invoice.exception.ClientException;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class ClientUtil {

  private ClientUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static final String ECOM_API_URL = "http://localhost:9090/api";

  public static Mono<Throwable> handleError(ClientResponse response, String message) {
    return response
        .createException()
        .map(webClientRespException -> new ClientException(message + response.rawStatusCode(), response.rawStatusCode()));
  }

}
