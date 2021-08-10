Feature: Health service Testing

  Background:
    Given Spring is running

  @HealthService
  Scenario: To validate health service is up
    Given send get request for the health service
    Then it should return status as up