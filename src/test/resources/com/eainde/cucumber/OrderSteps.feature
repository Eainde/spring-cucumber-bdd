Feature: Order service Testing

  Background:
    Given Spring is running

  @OrderService
  Scenario: Get order record
    Given path "/order/"
    And a request is made using method "GET"
    Then the request should respond with the status code 200
    Then the response body should match "orderResponseDto"

  @OrderService
  Scenario: Get order record by id
    Given path "/order/1"
    And a request is made using method "GET"
    Then the request should respond with the status code 200

  @OrderService
  Scenario: Add new order
    Given path "/order/"
    And request body is "orderRequestDto"
    And a request is made using method "POST"
    Then the request should respond with the status code 200