Feature: Notification Service Availability and Health Status

  As a system operator
  I want to ensure that the Notification service is healthy
  So that it can send messages reliably

  Scenario: Notification service should be healthy in Consul
    When I check the health of "notification" service
    Then and the service should be healthy in consul
    And the service actuator health should be UP

  Scenario: Send notification to a customer
    When I send a notification to customer with id 123, email "emma.mae@test.com", and message "Hello"
    Then the notification request should be successfully processed
    And the notification for email "emma.mae@test.com" exists in DB
    And delete the notification for email "emma.mae@test.com" from DB