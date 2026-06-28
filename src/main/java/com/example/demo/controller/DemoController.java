package com.example.demo.controller;

import com.example.demo.facade.DemoFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {
  private final DemoFacade facade;

  public DemoController(DemoFacade facade) {
    this.facade = facade;
  }

  @GetMapping("/demo")
  public Map<String, Object> demo() {
    return facade.handle();
  }
}
