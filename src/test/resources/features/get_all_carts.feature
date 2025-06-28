Feature: Get All Carts API

  Background:
    When Send a http "POST" request to "/auth/login" with body:
      """
      {
        "username": "emilys",
        "password": "emilyspass"
      }
      """
    And Save the token from the response to local storage

    Scenario: Simulate fetching all carts
        When Send a http "GET" request to "/carts" with body:
          """
          {}
          """
        Then The response status must be 200
        And The response schema should be match with schema "schemas/get_all_carts_schema.json"
        And Total carts in the response should be more than or equal to 0
        And Each cart should contain userId, products, total, discountedTotal, and totalQuantity