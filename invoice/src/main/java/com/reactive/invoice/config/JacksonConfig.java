package com.reactive.invoice.config;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper jacksonMapper() {
    return JsonMapper.builder()
              .configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
              .serializationInclusion(NON_NULL)
              .serializationInclusion(NON_EMPTY)
              .build();
  }

}
