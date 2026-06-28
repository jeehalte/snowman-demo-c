package com.example.demo.dto;

public class ServiceResult {
  private String service;
  private String message;

  public ServiceResult() {}

  public ServiceResult(String service, String message) {
    this.service = service;
    this.message = message;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
