package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.*;

public class OrderAssertions {
    @Step
    public static void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue());
    }

}
