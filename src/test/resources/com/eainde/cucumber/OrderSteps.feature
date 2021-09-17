Feature: Order service Testing

  Background:
    Given Spring is running
    And Setup database

  @OrderService
  Scenario: Get order record
    Given path "/order/"
    And Add following orders in database
    | orderId | country | description          | quantity |
    | 1       | India   | Packet of 10 pencils | 10       |
    | 2       | UK      | Packet of 5 chips    | 5        |
    | 3       | India   | 5 packet of eggs     | 5        |
    | 4       | UK      | MILK                 | 6        |
    And a request is made using method "GET"
    Then the request should respond with the status code 200
    And the response body should match "allOrderResponseDto"

  @OrderService
  Scenario: Get order record by id
    Given path "/order/1"
    And Add following orders in database
      | orderId | country | description          | quantity |
      | 1       | India   | Packet of 10 pencils | 10       |
    And a request is made using method "GET"
    Then the request should respond with the status code 200
    And the response body should match "orderResponseDto"

  @OrderService
  Scenario: Add new order
    Given path "/order/"
    And request body is "orderRequestDto"
    And a request is made using method "POST"
    Then the request should respond with the status code 200