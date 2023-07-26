
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import org.example.courier.Courier;
import org.example.courier.CourierAssertions;
import org.example.courier.CourierClient;
import org.example.courier.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String API_V1 = "/api/v1";
    private final CourierClient courierClient = new CourierClient();
    private final CourierGenerator courierGenerator = new CourierGenerator();
    private final Set<Integer> createdCourierIds = new HashSet<>();

    @Before
    public void setup() {
        given().baseUri(BASE_URI).basePath(API_V1).contentType(ContentType.JSON).when().get("/ping").then().statusCode(200);
    }

    @After
    public void cleanup() {
        for (int courierId : createdCourierIds) {
            courierClient.deleteCourier(String.valueOf(courierId));
        }
    }

    @Test
    public void courierLoginTest() {
        Courier courier = courierGenerator.generateRandomCourier();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        CourierAssertions.createdSuccessfully(createResponse);

        ValidatableResponse loginResponse = courierClient.logIn(courier);
        CourierAssertions.loggedInSuccessfully(loginResponse);

        int courierId = loginResponse.extract().path("id");
        createdCourierIds.add(courierId);
    }

    @Test
    public void courierLoginInvalidCredentialsTest() {
        Courier courier = courierGenerator.generateRandomCourier();

        ValidatableResponse createCourierResponse = courierClient.createCourier(courier);
        CourierAssertions.createdSuccessfully(createCourierResponse);

        ValidatableResponse loginResponse = courierClient.logIn(courier);
        CourierAssertions.loggedInSuccessfully(loginResponse);

        courier.setPassword("");

        ValidatableResponse invalidLoginResponse = courierClient.logIn(courier);

        invalidLoginResponse.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        int courierId = loginResponse.extract().path("id");
        createdCourierIds.add(courierId);
    }

    @Test
    public void courierLoginNonExistentTest() {
        Courier courier = courierGenerator.generateRandomCourier();
        ValidatableResponse response = courierClient.logIn(courier);

        response.assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}