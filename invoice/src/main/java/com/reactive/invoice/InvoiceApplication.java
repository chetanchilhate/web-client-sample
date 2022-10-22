package com.reactive.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(value = "com.reactive.invoice")
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}

}
