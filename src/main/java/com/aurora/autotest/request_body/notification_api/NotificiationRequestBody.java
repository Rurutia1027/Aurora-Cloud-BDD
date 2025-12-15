package com.aurora.autotest.request_body.notification_api;

import com.alibaba.fastjson.JSONObject;

public class NotificiationRequestBody {
    public static String build(Integer toCustomerId, String toCustomerEmail, String message) {
        JSONObject json = new JSONObject();
        json.put("toCustomerId", toCustomerId);
        json.put("toCustomerEmail", toCustomerEmail);
        json.put("message", message);
        return json.toJSONString();
    }
}
