Feature: Customer Management
  As a system
  I want to manage customers
  So that I can verify their data

  Scenario: Create a new customer
    Given a new customer with name "Alice" and email "alice@example.com"
    When the customer is saved
    Then the customer should exist in the database
