package com.reactive.invoice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client")
public record ClientConfig(
    int connectionTimeout,
    long readTimeout,
    long responseTimeout,
    int maxInMemorySize
) {}
