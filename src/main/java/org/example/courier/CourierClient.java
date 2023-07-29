package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;

public class CourierClient extends Client {
    static final String COURIER_API = "/courier";

    @Step
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_API)
                .then().log().all();
    }

    @Step
    public ValidatableResponse logIn(Courier courier) {
        Credentials credentials = Credentials.from(courier);
        return spec()
                .body(credentials)
                .when()
                .post(COURIER_API + "/login")
                .then().log().all();
    }

    @Step
    public void deleteCourier(String courierId) {
        spec()
                .when()
                .delete(COURIER_API + "/" + courierId)
                .then().log().all();
    }

}