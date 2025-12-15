Feature: Fraud Service Availability and Health Status

  As a system operator
  I want to ensure that the Fraud service is healthy
  So that it can process requests reliably

  Scenario: Fraud service should be healthy in Consul
    When I check the health of "fraud" service
    Then and the service should be healthy in consul
    And the service actuator health should be UP