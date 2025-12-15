package com.aurora.autotest.bdd.common;

import com.aurora.autotest.bdd.context.EnvironmentContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestClient {
    static {
        RestAssured.baseURI = EnvironmentContext.gatewayBaseUrl();
    }

    public static Response post(String path, String body) {
        return RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(path);
    }
}
