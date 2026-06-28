package com.example.demo.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class ExternalRestClient {
  private static final Logger log = LoggerFactory.getLogger(ExternalRestClient.class);
  private final RestClient restClient;

  public ExternalRestClient(RestClient.Builder builder) {
    // using open-meteo API for weather data
    this.restClient = builder.baseUrl("https://api.open-meteo.com").build();
  }

  /**
   * Performs the external REST call to Open-Meteo API and returns the parsed JSON as a Map.
   * This method is blocking and naturally works well with virtual threads.
   */
  public Map<String, Object> fetchRemote() {
    log.info("ExternalRestClient: calling Open-Meteo API (this should appear at most once per HTTP request)");
    return restClient.get()
            .uri("/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m")
            .retrieve()
            .body(Map.class);
  }
}
