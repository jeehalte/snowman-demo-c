package com.example.demo.service;

import com.example.demo.dto.ServiceResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ServiceC {
  private final com.example.demo.cache.PerRequestExternalCache cache;

  public ServiceC(com.example.demo.cache.PerRequestExternalCache cache) {
    this.cache = cache;
  }

  public ServiceResult process() {
    Map<String, Object> weatherData = cache.get();
    Map<String, Object> hourly = (Map<String, Object>) weatherData.get("hourly");
    List<?> times = (List<?>) hourly.get("time");
    return new ServiceResult("ServiceC", "Hourly forecast available for " + times.size() + " hours");
  }
}
