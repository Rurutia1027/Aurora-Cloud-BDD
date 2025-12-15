Feature: API Gateway service availability and health

  As a system user
  I want to ensure the API Gateway service is running and healthy
  So that I can reliably access underlying Aurora services

  Scenario: API Gateway service should be healthy in Consul
    When I check the health of "apigw" service
    Then and the service should be healthy in consul
    And the service actuator health should be UP
