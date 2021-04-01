package com.eainde.cucumber;

import java.io.IOException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BddConfig {
  @Bean
  public RestTemplate bddRestTemplate() {
    return new RestTemplateBuilder()
        .errorHandler(
            new ResponseErrorHandler() {
              @Override
              public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
              }

              @Override
              public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {}
            })
        .build();
  }
}
