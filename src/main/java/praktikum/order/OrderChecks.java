package praktikum.order;

import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.assertEquals;
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

    public void checkBadRequestCreated(ValidatableResponse orderResponse){
        String message = orderResponse
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals(message,"Ingredient ids must be provided");
    }

    public void checkCreatedWithWrongIngredients(ValidatableResponse orderResponse){
        String message = orderResponse
                .assertThat()
                .statusCode(500)
                .extract()
                .response().getBody().asString();

        assertTrue(message.contains("Internal Server Error"));
    }

    public void checkGetOrdersWithAuthorization(ValidatableResponse orderResponse){
        boolean created = orderResponse
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");

        assertTrue(created);
    }

    public void checkGetOrdersWithoutAuthorization(ValidatableResponse orderResponse){
        String message = orderResponse
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");

        assertEquals(message, "You should be authorised");
    }
}
