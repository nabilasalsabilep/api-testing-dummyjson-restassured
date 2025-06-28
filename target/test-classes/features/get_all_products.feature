Feature: Get All Products API

  Background:
    When Send a http "POST" request to "/auth/login" with body:
      """
      {
        "username": "emilys",
        "password": "emilyspass"
      }
      """
    And Save the token from the response to local storage

    Scenario: Validate structure and contents of Get All Products response
        When Send a http "GET" request to "/products" with body:
          """
          {}
          """
        Then The response status must be 200
        And The response schema should be match with schema "schemas/get_all_products_schema.json"
        And The total number of products must be more than 0
        And Each product in the response must contain id, title, price, and minimumOrderQuantity