package com.reactive.ecom.controllers;

import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

import com.reactive.ecom.dto.Customer;
import com.reactive.ecom.dto.ErrorResponse;
import com.reactive.ecom.dto.Order;
import com.reactive.ecom.dto.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * id 1 : customer 4xx error
 * id 2 : customer 5xx error
 * id 3 : product 4xx error
 * id 4 : product 5xx error
 * id 5 : order 4xx error
 * id 6 : order 5xx error
 * id 7 : quick response
 * id 8 : slow response
 */
@RestController
@RequestMapping("api/v1")
public class EcomController {

  private static final String ERROR_MSG_5XX = "Gateway Timeout Custom";
  public static final String ERROR_MSG_4XX = "Not Acceptable Custom";

  @GetMapping("/customers/{id}")
  public ResponseEntity<Object> getCustomerById(@PathVariable("id") String id) throws InterruptedException {
    switch (Integer.parseInt(id) % 8) {
      case 0:
        Thread.sleep(2000L);
        return ResponseEntity.ok(new Customer(id, "Vicky", "Mumbai"));
      case 1:
      case 2:
        if (Integer.parseInt(id) % 2 != 0)
          return ResponseEntity.status(NOT_ACCEPTABLE).body(new ErrorResponse("101", ERROR_MSG_4XX));
        return ResponseEntity.status(GATEWAY_TIMEOUT).body(new ErrorResponse("102", ERROR_MSG_5XX));
      default:
        return ResponseEntity.ok(new Customer(id, "Vicky", "Mumbai"));
    }
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<Object> getProductById(@PathVariable("id") String id) throws InterruptedException {
    switch (Integer.parseInt(id) % 8) {
      case 0:
        Thread.sleep(3000L);
        return ResponseEntity.ok(new Product(id, "Macbook", 162499));
      case 3:
      case 4:
        if (Integer.parseInt(id) % 2 != 0)
          return ResponseEntity.status(NOT_ACCEPTABLE).body(new ErrorResponse("103", ERROR_MSG_4XX));
        return ResponseEntity.status(GATEWAY_TIMEOUT).body(new ErrorResponse("104", ERROR_MSG_5XX));
      default:
        return ResponseEntity.ok(new Product(id, "Macbook", 162499));
    }
  }

  @GetMapping("/orders/{id}")
  public ResponseEntity<Object> getOrderById(@PathVariable("id") String id) throws InterruptedException {
    switch (Integer.parseInt(id) % 8) {
      case 0:
        Thread.sleep(3000L);
        return ResponseEntity.ok(new Order(id, 167800));
      case 5:
      case 6:
        if (Integer.parseInt(id) % 2 != 0)
          return ResponseEntity.status(NOT_ACCEPTABLE).body(new ErrorResponse("105", ERROR_MSG_4XX));
        return ResponseEntity.status(GATEWAY_TIMEOUT).body(new ErrorResponse("106", ERROR_MSG_5XX));
      default:
        return ResponseEntity.ok(new Order(id, 167800));
    }
  }

}
