package com.reactive.invoice.service;

import com.reactive.invoice.clients.CustomerClient;
import com.reactive.invoice.clients.OrderClient;
import com.reactive.invoice.clients.ProductClient;
import com.reactive.invoice.dto.Customer;
import com.reactive.invoice.dto.Invoice;
import com.reactive.invoice.dto.Order;
import com.reactive.invoice.dto.Product;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@Service
public class InvoiceService {

  private static final Function<Tuple3<Customer, Product, Order>, Invoice>
      TUPLE_TO_INVOICE = tuple -> new Invoice(tuple.getT1(), tuple.getT2(), tuple.getT3());

  private final CustomerClient customerClient;
  private final ProductClient productClient;
  private final OrderClient orderClient;

  public InvoiceService(CustomerClient customerClient, ProductClient productClient, OrderClient orderClient) {
    this.customerClient = customerClient;
    this.productClient = productClient;
    this.orderClient = orderClient;
  }

  public Mono<Invoice> getInvoiceById(int id) {
    Mono<Customer> customer = customerClient.getCustomerById(id);
    Mono<Product> product = productClient.getProductById(id);
    Mono<Order> order = orderClient.getOrderById(id);
    return Mono.zip(customer, product, order).map(TUPLE_TO_INVOICE);
  }

}
