Feature: Customer registration

  As a system user
  I want to register a new customer
  So that the customer can use Aurora services

  Scenario: Register a new customer successfully
    When I register a customer with first name "Emma", last name "Mae", and email "emma.mae@test.com"
    Then the customer registration should be successful
    And the customer with email "emma.mae@test.com" exists in DB
    And delete the customer with email "emma.mae@test.com" from DB