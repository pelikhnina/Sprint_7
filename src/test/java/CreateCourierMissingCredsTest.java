import io.restassured.response.ValidatableResponse;
import org.example.courier.Courier;
import org.example.courier.CourierClient;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.example.courier.CourierGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierMissingCredsTest {

    private final CourierGenerator courierGenerator = new CourierGenerator();
    private final CourierClient courierClient = new CourierClient();
    private final Set<String> createdCourierLogins = new HashSet<>();
    @After
    public void cleanup() {
        for (String login : createdCourierLogins) {
            courierClient.deleteCourier(login);
        }
    }
    @Parameterized.Parameter()
    public String login;
    @Parameterized.Parameter(1)
    public String password;

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {null, "password123"},
                {"testCourier", null},
                {null, null}
        });
    }

    @Test
    public void createCourierMissingCredentials() {
        String firstName = courierGenerator.generateRandomName(6);
        Courier courier = new Courier(login, password, firstName);

        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        createdCourierLogins.add(courier.getLogin());
    }

}
