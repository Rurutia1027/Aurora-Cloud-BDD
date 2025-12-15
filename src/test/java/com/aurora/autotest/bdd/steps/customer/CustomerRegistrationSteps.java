package com.aurora.autotest.bdd.steps.customer;

import com.aurora.autotest.bdd.common.RestClient;
import com.aurora.autotest.bdd.context.ApiContext;
import com.aurora.autotest.request_body.customer_api.CustomerRegistrationRequestBody;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerRegistrationSteps {
    private Response response;

    @When("I register a customer with first name {string}, last name {string}, and email " +
            "{string}")
    public void register_customer(String firstName, String lastName, String email) {
        String body = CustomerRegistrationRequestBody.build(firstName, lastName, email);

        response = RestClient.post(
                ApiContext.path("customer.register"),
                body
        );
    }

    @Then("the customer registration should be successful")
    public void the_customer_registration_should_be_successful() {
        assertThat(response.statusCode()).isEqualTo(200);
    }
}
