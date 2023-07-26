package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;

public class OrderClient extends Client {
    static final String ORDER_API = "/orders";
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_API)
                .then().log().all();
    }
    @Step
    public ValidatableResponse getOrderByTrack(int trackId) {
        return spec()
                .when()
                .get(ORDER_API + "/track?t=" + trackId )
                .then().log().all();
    }
    @Step
    public ValidatableResponse getOrders() {
        return spec()
                .when()
                .get(ORDER_API + "?courierId=")
                .then().log().all();
    }
    @Step
    public ValidatableResponse cancelOrder(int trackId) {
        return spec()
                .body(trackId)
                .when()
                .put(ORDER_API + "/cancel")
                .then().log().all();
    }
}