package com.aurora.autotest.bdd.steps.health;

import com.aurora.autotest.bdd.common.RestClient;
import com.aurora.autotest.bdd.context.EnvironmentContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;

public class ServiceHealthSteps {
    private Response response;

    @When("I check the health of {string} service")
    public void check_health_of_service(String serviceName) {
        String consulHealthUrl =
                EnvironmentContext.consulBaseUrl()
                        + "/v1/health/service/"
                        + serviceName.trim()
                        + "?passing=true";

        response = RestClient.get(consulHealthUrl, null);
    }

    @Then("the service actuator health should be UP")
    public void actuator_health_should_be_up() {

        response.then()
                .body("[0].Checks.find { it.CheckID.startsWith('service:') }.Output",
                        containsString("\"status\":\"UP\""));
    }

    @Then("and the service should be healthy in consul")
    public void service_should_be_healthy_in_consul() {
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].Checks.Status", everyItem(equalTo("passing")));
    }
}
