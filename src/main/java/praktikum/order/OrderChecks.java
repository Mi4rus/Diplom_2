package praktikum.order;

import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.assertTrue;

public class OrderChecks {
    public void checkCreated(ValidatableResponse orderResponse){
        boolean created = orderResponse
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");

        assertTrue(created);
    }
}
