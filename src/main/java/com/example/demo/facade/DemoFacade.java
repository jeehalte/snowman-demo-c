package com.example.demo.facade;

import com.example.demo.dto.ServiceResult;
import com.example.demo.service.ServiceA;
import com.example.demo.service.ServiceB;
import com.example.demo.service.ServiceC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoFacade {
  private static final Logger log = LoggerFactory.getLogger(DemoFacade.class);

  private final ServiceA serviceA;
  private final ServiceB serviceB;
  private final ServiceC serviceC;

  public DemoFacade(ServiceA serviceA, ServiceB serviceB, ServiceC serviceC) {
    this.serviceA = serviceA;
    this.serviceB = serviceB;
    this.serviceC = serviceC;
  }

  public List<ServiceResult> handle() {
    log.info("Facade starting: calling services A, B, C");
    ServiceResult a = serviceA.process();
    ServiceResult b = serviceB.process();
    ServiceResult c = serviceC.process();
    log.info("Facade completed");
    return List.of(a, b, c);
  }
}
