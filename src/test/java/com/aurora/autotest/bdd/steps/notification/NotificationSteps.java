package com.aurora.autotest.bdd.steps.notification;

import com.aurora.autotest.bdd.common.DBHandler;
import com.aurora.autotest.bdd.common.RestClient;
import com.aurora.autotest.bdd.context.ApiContext;
import com.aurora.autotest.request_body.notification_api.NotificiationRequestBody;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NotificationSteps {
    private Response response;

    @When("I send a notification to customer with id {int}, email {string}, and message " +
            "{string}")
    public void send_notification(Integer toCustomerId, String email, String message) {
        String body = NotificiationRequestBody.build(toCustomerId, email, message);
        response = RestClient.post(
                ApiContext.path("notification.send"),
                body,
                null
        );
    }

    @Then("the notification request should be successfully processed")
    public void notification_should_be_successful() {
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Then("the notification for email {string} exists in DB")
    public void notification_should_exist_in_db(String email) {
        DBHandler dbHandler = DBHandler.getInstance("notification");
        String query = "SELECT 1 FROM notification WHERE to_customer_email = ?";
        boolean exists = dbHandler.recordExists(query, email);
        assertThat(exists).isTrue();
    }

    @Then("delete the notification for email {string} from DB")
    public void delete_notification_from_db(String email) {
        DBHandler dbHandler = DBHandler.getInstance("notification");
        String query = "DELETE FROM notification WHERE to_customer_email = ?";
        int rowsDeleted = dbHandler.executeUpdate(query, email);
        assertThat(rowsDeleted).isGreaterThan(0);
    }
}
