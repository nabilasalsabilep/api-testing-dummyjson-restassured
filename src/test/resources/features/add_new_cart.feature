Feature: Add New Cart API

  Background:
    When Send a http "POST" request to "/auth/login" with body:
      """
      {
        "username": "emilys",
        "password": "emilyspass"
      }
      """
    And Save the token from the response to local storage

    Scenario: Simulate adding a new cart
      Given Make sure token in local storage not empty
      When Send a http "GET" request to "/products" with body:
        """
        {}
        """
      And Get first product & second product from the list of products
      When Send a http "POST" request to "/carts/add" with body:
        """
        {
          "userId": 1,
          "products": [
            {
              "id": "{id1}",
              "quantity": "{qty1}"
            },
            {
              "id": "{id2}",
              "quantity": "{qty2}"
            }
          ]
        }
        """
      Then The response status must be 201
      And The response schema should be match with schema "schemas/add_new_cart_schema.json"
      And The user id in the response must be 1
      And Cart id in the response must be created
      And First product id in the response must be {id1}
      And Title of the first product in the response must be the same as the title of the first product in the list
      And Price of the first product in the response must be the same as the price of the first product in the list
      And Quantity of the first product in the response must be {qty1}
      And Sub total of the first product must be equal to price of the first product that calculated with quantity of the first product
      And Discount percentage of the first product in the response must be the equal as the discount percentage of the first product in the list, if present
      And Discounted price of the first product in the response must be equal to the calculated discounted price
      And Second product id in the response must be {id2}
      And Title of the second product in the response must be the same as the title of the second product in the list
      And Price of the second product in the response must be the same as the price of the second product in the list
      And Quantity of the second product in the response must be {qty2}
      And Sub total of the second product must be equal to price of the second product that calculated with quantity of the second product
      And Discount percentage of the second product in the response must be the equal as the discount percentage of the second product in the list, if present
      And Discounted price of the second product in the response must be equal to the calculated discounted price
      And Total product in the response must be 2
      And Total discounted price in the response must be equal to the sum of the two discounted prices
      And Total quantity in the response must be equal to the sum of the quantities
      And The Total in the response must be equal to the sum of the two product prices that calculated with quantity