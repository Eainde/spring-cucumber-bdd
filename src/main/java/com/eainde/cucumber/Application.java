package com.eainde.cucumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.eainde.cucumber"})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
