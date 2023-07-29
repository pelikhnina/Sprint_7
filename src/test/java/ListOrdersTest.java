import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.order.OrderClient;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class ListOrdersTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String API_V1 = "/api/v1";
    private final OrderClient orderClient = new OrderClient();

    @Before
    public void setup() {
        given().baseUri(BASE_URI).basePath(API_V1).contentType(ContentType.JSON).when().get("/ping").then().statusCode(200);
    }

    @Test
    public void listOrdersTest() {
        ValidatableResponse response = orderClient.getOrders();

        response.assertThat()
                .statusCode(200)
                .body("orders", not(empty()));
    }
}