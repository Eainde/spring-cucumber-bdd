Feature: Info service Testing

  Background:
    Given Spring is running

  @InfoService
  Scenario: To validate info service is up
    Given send get request for the info service
    Then it should return info status as up