package com.example.demo.controller;

import com.example.demo.dto.ServiceResult;
import com.example.demo.facade.DemoFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
  private final DemoFacade facade;

  public DemoController(DemoFacade facade) {
    this.facade = facade;
  }

  @GetMapping("/demo")
  public List<ServiceResult> demo() {
    return facade.handle();
  }
}
