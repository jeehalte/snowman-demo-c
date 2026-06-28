package com.example.demo.facade;

import com.example.demo.dto.ServiceResult;
import com.example.demo.external.ExternalRestClient;
import com.example.demo.service.ServiceA;
import com.example.demo.service.ServiceB;
import com.example.demo.service.ServiceC;
import com.example.demo.cache.PerRequestExternalCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DemoFacade {
  private static final Logger log = LoggerFactory.getLogger(DemoFacade.class);

  private final ExternalRestClient externalRestClient;
  private final PerRequestExternalCache cache;
  private final ServiceA serviceA;
  private final ServiceB serviceB;
  private final ServiceC serviceC;

  public DemoFacade(ExternalRestClient externalRestClient, PerRequestExternalCache cache,
                    ServiceA serviceA, ServiceB serviceB, ServiceC serviceC) {
    this.externalRestClient = externalRestClient;
    this.cache = cache;
    this.serviceA = serviceA;
    this.serviceB = serviceB;
    this.serviceC = serviceC;
  }

  public Map<String, Object> handle() {
    log.info("Facade starting: fetching weather data from Open-Meteo API");
    
    // Fetch weather data from external API
    Map<String, Object> weatherData = externalRestClient.fetchRemote();
    
    // Store in cache for services to use
    cache.set(weatherData);
    
    // Call all services to process the weather data
    log.info("Facade: calling all services");
    ServiceResult resultA = serviceA.process();
    ServiceResult resultB = serviceB.process();
    ServiceResult resultC = serviceC.process();
    
    log.info("Service results: {} | {} | {}", resultA.getService(), resultB.getService(), resultC.getService());
    
    // Return the complete weather data with service results embedded
    Map<String, Object> response = new java.util.LinkedHashMap<>(weatherData);
    response.put("service_results", List.of(resultA, resultB, resultC));
    
    log.info("Facade completed");
    return response;
  }
}
