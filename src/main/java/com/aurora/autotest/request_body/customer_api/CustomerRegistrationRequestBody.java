package com.aurora.autotest.request_body.customer_api;
import com.alibaba.fastjson.JSONObject;

/**
 * Helper class to bulid JSON request bodies
 */
public class CustomerRegistrationRequestBody {
    public static String build(String firstName, String lastName, String email) {
        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("email", email);
        return json.toJSONString();
    }
}
