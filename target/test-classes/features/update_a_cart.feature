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

    Scenario: Simulate updating a cart
      Given Make sure token in local storage not empty
      When Send a http "GET" request to "/carts" with body:
        """
        {}
        """
      And Get first cart from the list of carts
      When Send a http "PUT" request to "/carts/{cartId}" with body:
        """
        {
          "userId": 1,
          "merge": false,
          "products": [
            {
              "id": "{productId1}",
              "quantity": "{productQty1}"
            },
            {
              "id": "{productId2}",
              "quantity": "{productQty2}"
            }
          ]
        }
        """
      Then The response status must be 200
      And The response schema should be match with schema "schemas/update_a_cart_schema.json"
      And The user id in the response must be 1
      And Cart id in the response must be {cartId}
      And Product id in the response must be the same as first and second product id
      And Product titles of the first product in the response must not be changed
      And Product prices in the response must not be changed
      And Product quantities in the response must be the same as first and second product quantities
      And Total of each product must be equal to price multiplied by quantity
      And Discount percentage of each product must be equal to the calculation between the discounted price and the product sub-total, if present
      And Discounted price of each product must be equal to calculated discounted total
      And Total number of products in the response must be 2
      And Total quantity in the response must be equal to the sum of all product quantities
      And The Total in the response must be equal to the sum of all product subtotals
      And Total discounted price in the response must be equal to the sum of all product discounted prices