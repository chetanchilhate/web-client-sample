package com.reactive.invoice.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus code, String message) {

  public int getCode() {
    return code.value();
  }

}
