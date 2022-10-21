package com.reactive.invoice.dto;

import java.util.Random;
import reactor.util.function.Tuple3;

public record Invoice(String customerName, String customerAddress, String productName, double price, double total) {

  public Invoice(Customer customer, Product product, Order order) {
    this(customer.name(), customer.address(), product.name(), product.price(), order.total());
  }

  public int getInvoiceNo() {
    return new Random().nextInt(100, 1000);
  }

}
