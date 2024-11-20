package praktikum.order;

import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

    public ArrayList <String> checkGetIngredients(ValidatableResponse dataResponse){
        ArrayList <String> id = dataResponse
                .assertThat()
                .statusCode(200)
                .extract()
                .path("data._id");
        assertNotNull(id);

        return id;
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
