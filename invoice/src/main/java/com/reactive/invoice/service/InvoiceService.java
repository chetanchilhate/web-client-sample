package com.reactive.invoice.service;

import com.reactive.invoice.clients.CustomerClient;
import com.reactive.invoice.clients.OrderClient;
import com.reactive.invoice.clients.ProductClient;
import com.reactive.invoice.dto.Customer;
import com.reactive.invoice.dto.Invoice;
import com.reactive.invoice.dto.Order;
import com.reactive.invoice.dto.Product;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

  private final CustomerClient customerClient;
  private final ProductClient productClient;
  private final OrderClient orderClient;

  public InvoiceService(CustomerClient customerClient, ProductClient productClient, OrderClient orderClient) {
    this.customerClient = customerClient;
    this.productClient = productClient;
    this.orderClient = orderClient;
  }

  public Invoice getInvoiceById(String id) {
    Customer customer = customerClient.getCustomerById(id);
    Product product = productClient.getProductById(id);
    Order order = orderClient.getOrderById(id);
    return new Invoice(customer, product, order);
  }

}
