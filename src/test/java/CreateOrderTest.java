import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import org.example.order.Order;
//import org.example.order.OrderAssertions;
import org.example.order.OrderAssertions;
import org.example.order.OrderClient;
import org.example.order.OrderGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
@RunWith(Parameterized.class)
public class CreateOrderTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String API_V1 = "/api/v1";
    private final OrderClient orderClient = new OrderClient();
    private final OrderGenerator orderGenerator = new OrderGenerator();
    private Set<Integer> createdOrderTracks = new HashSet<>();

    @Parameterized.Parameter
    public String[] colors;

    @Parameterized.Parameters
    public static Collection<Object[]> colorsProvider() {
        return Arrays.asList(new Object[][] {
                { new String[]{"BLACK"} },
                { new String[]{"GREY"} },
                { new String[]{"BLACK", "GREY"} },
                { null }
        });
    }

    @Before
    public void setup() {
        given().baseUri(BASE_URI).basePath(API_V1).contentType(ContentType.JSON).when().get("/ping").then().statusCode(200);
    }

    @After
    public void cleanup() {
        for (Integer track : createdOrderTracks) {
            orderClient.cancelOrder(track);
        }
    }

    @Test
    public void createOrderTest() {
        Order order = orderGenerator.generateRandomOrder();
        ValidatableResponse response = orderClient.createOrder(order);

        response.assertThat()
                .statusCode(201)
                .body("track", notNullValue());

        int track = response.extract().path("track");
        createdOrderTracks.add(track);
    }

    @Test
    public void createOrderWithColorsTest() {
        Order order = orderGenerator.generateRandomOrder();
        order.setColors(colors);

        ValidatableResponse response = orderClient.createOrder(order);

        // Assertion
        response.assertThat()
                .statusCode(201)
                .body("track", notNullValue());

        OrderAssertions.createdSuccessfully(response);

        int track = response.extract().path("track");
        createdOrderTracks.add(track);
    }
}