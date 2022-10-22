package com.reactive.invoice.exception.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.reactive.invoice.dto.ErrorResponse;
import com.reactive.invoice.exception.ClientException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler(ClientException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorResponse handleClientException(ClientException ex) {
    return new ErrorResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
  }

}
