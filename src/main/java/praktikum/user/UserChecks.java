package praktikum.user;

import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.*;

public class UserChecks {
    public void checkCreated(ValidatableResponse response){
        boolean created = response
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");

        assertTrue(created);
    }
    public void checkDuplicateForbidden(ValidatableResponse response){
        String message = response
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");
        assertEquals(message,"User already exists");
    }

    public void checkBadRequestForbidden(ValidatableResponse response){
        String message = response
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");
        assertEquals(message,"Email, password and name are required fields");
    }

    public String checkLoggedIn(ValidatableResponse loginResponse){
        String token = loginResponse
                .assertThat()
                .statusCode(200)
                .extract()
                .path("accessToken");
        assertNotNull(token);
        return token;
    }

    public void checkBadRequestUnauthorized(ValidatableResponse loginResponse){
        String message = loginResponse
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
        assertEquals(message,"email or password are incorrect");

    }

    public void deleteUser(ValidatableResponse response){
        String message = response
                .assertThat()
                .statusCode(202)
                .extract()
                .path("message");
        assertEquals(message,"User successfully removed");
    }

}
