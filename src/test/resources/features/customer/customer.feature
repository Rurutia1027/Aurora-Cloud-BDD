Feature: Customer service availability and registration

  As a system user
  I want to ensure the customer service is healthy
  And be able to register a new customer
  So that Aurora services can be used reliably

  Scenario: Customer service should be healthy in Consul
    When I check the health of "customer" service
    Then and the service should be healthy in consul
    And the service actuator health should be UP

#  Scenario: Register a new customer successfully
#    When I register a customer with first name "Emma", last name "Mae", and email "emma.mae@test.com"
#    Then the customer registration should be successful
#    And the customer with email "emma.mae@test.com" exists in DB
#    And delete the customer with email "emma.mae@test.com" from DB