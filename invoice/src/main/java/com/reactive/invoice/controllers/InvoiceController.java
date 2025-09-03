package com.reactive.invoice.controllers;

import com.reactive.invoice.dto.Invoice;
import com.reactive.invoice.service.InvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * id 1 : customer 5xx error
 * id 2 : customer 4xx error
 * id 3 : product 5xx error
 * id 4 : product 4xx error
 * id 5 : order 5xx error
 * id 6 : order 4xx error
 * id 7 : quick response
 * id 8 : slow response
 */
@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

  private final InvoiceService invoiceService;

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/{id}")
  Mono<Invoice> getInvoiceById(@PathVariable int id) {
    return invoiceService.getInvoiceById(id);
  }

}
