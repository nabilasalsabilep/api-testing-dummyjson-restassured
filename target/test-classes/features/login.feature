Feature: Login API

  Scenario: Simulate successful login
    When Send a http "POST" request to "/auth/login" with body:
      """
      {
        "username": "emilys",
        "password": "emilyspass"
      }
      """
    Then The response status must be 200
    And The response schema should be match with schema "schemas/login_schema.json"
    And Save the token from the response to local storage
    And Username in the response must be "emilys"
    And Email in the response must be "emily.johnson@x.dummyjson.com"
    And First name in the response must be "Emily"
    And Last name in the response must be "Johnson"
    And Gender in the response must be "female"
    And Image url in the response must be "https://dummyjson.com/icon/emilys/128"