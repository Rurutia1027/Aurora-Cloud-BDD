package com.aurora.autotest.bdd.common;

import com.aurora.autotest.bdd.context.EnvironmentContext;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestClient {
    static {
        RestAssured.baseURI = EnvironmentContext.gatewayBaseUrl();
    }

    private static final String DEFAULT_CONTENT_TYPE = "application/json";
    private static final String DEFAULT_ACCEPT = "application/json";

    /**
     * Common request function
     *
     * @param method      GET,  POST,  PUT, DELETE, PATCH
     * @param url         complete URL
     * @param body        request body, null is allowed.
     * @param xAuthToken  X-AUTH-TOKEN, null is allowed.
     * @param contentType Content-Type, null is allowed
     * @param accept      Accept header, null is allowed
     */
    public static Response sendRequest(String method, String url, String body, String xAuthToken,
                                       String contentType, String accept) {

        contentType = contentType == null ? DEFAULT_CONTENT_TYPE : contentType;
        accept = accept == null ? DEFAULT_ACCEPT : accept;

        var requestSpec = given()
                .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .header("Content-Type", contentType)
                .header("Accept", accept);

        if (xAuthToken != null && !xAuthToken.isEmpty()) {
            requestSpec.header("X-AUTH-TOKEN", xAuthToken);
        }

        if (body != null) {
            requestSpec.body(body);
        }

        return switch (method.toUpperCase()) {
            case "POST" -> requestSpec.post(url);
            case "PUT" -> requestSpec.put(url);
            case "GET" -> requestSpec.get(url);
            case "DELETE" -> requestSpec.delete(url);
            case "PATCH" -> requestSpec.patch(url);
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        };
    }

    // simpler functions
    public static Response post(String url, String body, String xAuthToken) {
        return sendRequest("POST", url, body, xAuthToken, null, null);
    }

    public static Response get(String url, String xAuthToken) {
        return sendRequest("GET", url, null, xAuthToken, null, null);
    }

    public static Response put(String url, String body, String xAuthToken) {
        return sendRequest("PUT", url, body, xAuthToken, null, null);
    }

    public static Response delete(String url, String xAuthToken) {
        return sendRequest("DELETE", url, null, xAuthToken, null, null);
    }

    public static Response patch(String url, String body, String xAuthToken) {
        return sendRequest("PATCH", url, body, xAuthToken, null, null);
    }
}
