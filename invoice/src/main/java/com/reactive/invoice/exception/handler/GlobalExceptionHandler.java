package com.reactive.invoice.exception.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.reactive.invoice.dto.ErrorResponse;
import com.reactive.invoice.exception.ClientException;
import io.netty.handler.timeout.TimeoutException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler(ClientException.class)
  public ResponseEntity<ErrorResponse> handleClientException(ClientException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(ErrorResponse.create(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage()));
  }

  @ExceptionHandler(TimeoutException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorResponse handleTimeoutException(TimeoutException ex) {
    return new ErrorResponse(INTERNAL_SERVER_ERROR, ex.getClass().getName());
  }

  @ExceptionHandler(WebClientResponseException.class)
  public ResponseEntity<ErrorResponse> handleWebClientResponseException(WebClientResponseException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(ErrorResponse.create(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getMessage()));
  }

}
