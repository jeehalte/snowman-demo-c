package com.example.demo.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.example.demo.external.ExternalRestClient;

import java.util.Map;

@Component
public class DemoFacade {
  private static final Logger log = LoggerFactory.getLogger(DemoFacade.class);

  private final ExternalRestClient externalRestClient;

  public DemoFacade(ExternalRestClient externalRestClient) {
    this.externalRestClient = externalRestClient;
  }

  public Map<String, Object> handle() {
    log.info("Facade starting: fetching weather data from Open-Meteo API");
    Map<String, Object> weatherData = externalRestClient.fetchRemote();
    log.info("Facade completed: weather data retrieved");
    return weatherData;
  }
}
