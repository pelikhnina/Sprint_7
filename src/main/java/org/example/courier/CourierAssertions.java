package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAssertions {

    @Step
    public static void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Step
    public static void loggedInSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }
}