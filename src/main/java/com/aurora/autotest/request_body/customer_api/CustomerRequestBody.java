package com.aurora.autotest.request_body.customer_api;
import com.alibaba.fastjson.JSONObject;

/**
 * Helper class to bulid JSON request bodies
 */
public class CustomerRequestBody {
    /**
     * Build request body for creating a new customer.
     *
     * @param name Customer's name
     * @param email Customer's email
     * @return  JSON string ready to send via REST call
     */
    public String buildCreateCustomerRequestyBody(String name, String email) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("email", email);

        return jsonObject.toString();
    }

    /**
     * Build request body for updating an existing customer.
     *
     * @param customerId ID of customer to update
     */
}
