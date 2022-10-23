package com.reactive.invoice.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class WebClientLogger {

  private static final Logger log = LoggerFactory.getLogger(WebClientLogger.class);

  public static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(request -> {
      logMethodAndUrl(request);
      logHeaders(request);

      return Mono.just(request);
    });
  }

  public static ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(response -> {
      logStatus(response);
      logHeaders(response);

      return logBody(response);
    });
  }

  private static void logStatus(ClientResponse response) {
    HttpStatus status = response.statusCode();
    log.debug("Status Code {} ({})", status.value(), status.getReasonPhrase());
  }

  private static Mono<ClientResponse> logBody(ClientResponse response) {
    if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
      return response.bodyToMono(String.class)
          .flatMap(body -> {
            log.error("Body {}", body);
            return Mono.just(response);
          });
    } else {
      return Mono.just(response);
    }
  }

  private static void logHeaders(ClientResponse response) {
    logHeaderMap(response.headers().asHttpHeaders());
  }

  private static void logHeaders(ClientRequest request) {
    logHeaderMap(request.headers());
  }

  private static void logHeaderMap(Map<String, List<String>> headerMap) {
    if (!headerMap.isEmpty()) {
      headerMap.entrySet()
          .stream()
          .filter(entry -> entry.getKey().contains(AUTHORIZATION))
          .forEach(entry -> log.info( "Header = {} : {}", entry.getKey(), entry.getValue()));

      log.debug("Headers = {}", headerMap);
    }
  }

  private static void logMethodAndUrl(ClientRequest request) {
    var requestStr = request.method().name() +
        " to " +
        request.url();
    log.debug(requestStr);
  }

}
