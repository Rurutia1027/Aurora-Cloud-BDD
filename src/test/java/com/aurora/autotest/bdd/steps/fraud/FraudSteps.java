package com.aurora.autotest.bdd.steps.fraud;

import com.aurora.autotest.bdd.common.DBHandler;
import com.aurora.autotest.bdd.common.RestClient;
import com.aurora.autotest.bdd.context.ApiContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FraudSteps {
    private Response response;

    @When("I check if customer {int} is fraudulent")
    public void check_fraud_status(Integer customerId) {
        String url = ApiContext.path("fraud.check").replace("{id}", customerId.toString());
        response = RestClient.get(url, null);
    }

    @Then("the fraud check result should be {string}")
    public void fraud_check_should_be(String expectedResult) {
        boolean isFraud = response.jsonPath().getBoolean("isFraudster");
        assertThat(Boolean.toString(isFraud)).isEqualTo(expectedResult);
    }

    @Then("the fraud record for customer {int} exists in DB")
    public void fraud_record_should_exist_in_db(Integer customerId) {
        DBHandler db = DBHandler.getInstance("fraud");
        String query = "SELECT 1 FROM fraud_check_history WHERE customer_id = ?";
        boolean exists = db.recordExists(query, customerId);
        assertThat(exists).isTrue();
    }

    @Then("delete the fraud record for customer {int} from DB")
    public void delete_fraud_record(Integer customerId) {
        DBHandler db = DBHandler.getInstance("fraud");
        String query = "DELETE FROM fraud_check_history WHERE customer_id = ?";
        int rowsDeleted = db.executeUpdate(query, customerId);
        assertThat(rowsDeleted).isGreaterThan(0);
    }
}
