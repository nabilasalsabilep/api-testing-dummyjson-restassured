Feature: Update A Cart API

  Background:
    When Send a http "POST" request to "/auth/login" with body:
      """
      {
        "username": "emilys",
        "password": "emilyspass"
      }
      """
    And Save the token from the response to local storage

    Scenario: Simulate deleting a cart
      Given Make sure token in local storage not empty
      When Send a http "GET" request to "/carts" with body:
        """
        {}
        """
      And Get first cart from the list of carts
      When Send a http "DELETE" request to "/carts/{cartId}" with body:
          """
          {}
          """
      Then The response status must be 200
      And The response schema should be match with schema "schemas/delete_a_cart_schema.json"
      And The "isDeleted" field in the response must be true
      And The "deletedOn" field in the response must not be null