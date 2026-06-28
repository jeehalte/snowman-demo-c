package com.example.demo.controller;

import com.example.demo.external.ExternalRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {
  private final ExternalRestClient externalRestClient;

  public DemoController(ExternalRestClient externalRestClient) {
    this.externalRestClient = externalRestClient;
  }

  @GetMapping("/demo")
  public Map<String, Object> demo() {
    return externalRestClient.fetchRemote();
  }
}
