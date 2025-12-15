Feature: Customer registration

  As a system user
  I want to register a new customer
  So that the customer can use Aurora services

  Scenario: Register a new customer successfully
    When I register a customer with first name "John", last name "Doe", and email "john.doex@test.com"
    Then the customer registration should be successful
