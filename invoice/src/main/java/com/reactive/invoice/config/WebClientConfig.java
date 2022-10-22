package com.reactive.invoice.config;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.reactive.invoice.clients.config.ClientConfig;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.Connection;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  private final ClientConfig clientConfig;

  public WebClientConfig(ClientConfig clientConfig) {
    this.clientConfig = clientConfig;
  }

  @Primary
  @Bean("basicWebClient")
  WebClient webClient() {
    return WebClient.builder()
        .clientConnector(getReactorClientHttpConnector())
        .exchangeStrategies(exchangeStrategies())
        .build();
  }

  private ReactorClientHttpConnector getReactorClientHttpConnector() {
    final var httpClient = HttpClient.create()
        .option(CONNECT_TIMEOUT_MILLIS, clientConfig.connectionTimeout())
        .doOnConnected(this::addReadTimeoutHandler)
        .responseTimeout(Duration.ofMillis(clientConfig.responseTimeout()));
    return new ReactorClientHttpConnector(httpClient);
  }

  private void addReadTimeoutHandler(Connection conn) {
    conn.addHandlerFirst(new ReadTimeoutHandler(clientConfig.readTimeout(), MILLISECONDS));
  }

  private ExchangeStrategies exchangeStrategies() {
    final var maxInMemorySize = clientConfig.maxInMemorySize() * 1024 * 1024;
    return ExchangeStrategies.builder()
        .codecs(configure -> configure.defaultCodecs()
            .maxInMemorySize(maxInMemorySize))
        .build();
  }
}
