Feature: Fraud service availability and health

  Scenario: Notification service should be healthy in Consul
    When I check the health of "fraud" service
    Then and the service should be healthy in consul
    And the service actuator health should be UP

  Scenario: Fraud check for customer
    When I check if customer 123 is fraudulent
    Then the fraud check result should be "false"
    And the fraud record for customer 123 exists in DB
    And delete the fraud record for customer 123 from DB
