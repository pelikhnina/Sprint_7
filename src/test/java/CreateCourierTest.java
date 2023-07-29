import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
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
public class CreateCourierTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String API_V1 = "/api/v1";
    private final CourierClient courierClient = new CourierClient();
    private final CourierGenerator courierGenerator = new CourierGenerator();
    private final Set<String> createdCourierLogins = new HashSet<>();

    @Before
    public void setup() {
        given().baseUri(BASE_URI).basePath(API_V1).contentType(ContentType.JSON).when().get("/ping").then().statusCode(200);
    }

    @After
    public void cleanup() {
        for (String login : createdCourierLogins) {
            courierClient.deleteCourier(login);
        }
    }

    @Test
    public void createCourierTest() {
        Courier courier = courierGenerator.generateRandomCourier();
        ValidatableResponse response = courierClient.createCourier(courier);
        CourierAssertions.createdSuccessfully(response);

        createdCourierLogins.add(courier.getLogin());
    }

    @Test
    public void createCourierDuplicateLoginTest() {
        Courier courier = courierGenerator.generateRandomCourier();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        CourierAssertions.createdSuccessfully(createResponse);

        ValidatableResponse duplicateResponse = courierClient.createCourier(courier);

        duplicateResponse.assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        createdCourierLogins.add(courier.getLogin());
    }
}