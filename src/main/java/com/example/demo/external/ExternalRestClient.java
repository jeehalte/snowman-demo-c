package com.example.demo.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class ExternalRestClient {
  private static final Logger log = LoggerFactory.getLogger(ExternalRestClient.class);
  private final WebClient webClient;

  public ExternalRestClient(WebClient.Builder builder) {
    // using httpbin.org for demo; replace with your real external service
    this.webClient = builder.baseUrl("https://httpbin.org").build();
  }

  /**
   * Performs the external REST call and returns the parsed JSON as a Map.
   * This method blocks the caller until the response is received.
   */
  public Map<String, Object> fetchRemote() {
    log.info("ExternalRestClient: calling external API (this should appear at most once per HTTP request)");
    return webClient.get()
            .uri("/get")
            .retrieve()
            .bodyToMono(Map.class)
            .block();
  }
}
