package com.example.demo.service;

import com.example.demo.dto.ServiceResult;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ServiceB {
  private final com.example.demo.cache.PerRequestExternalCache cache;

  public ServiceB(com.example.demo.cache.PerRequestExternalCache cache) {
    this.cache = cache;
  }

  public ServiceResult process() {
    Map<String, Object> external = cache.get();
    return new ServiceResult("ServiceB", "origin=" + external.get("origin"));
  }
}
