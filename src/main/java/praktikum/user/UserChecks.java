package praktikum.user;

import io.restassured.response.ValidatableResponse;

import java.util.List;

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
    public void checkDuplicateCreateForbidden(ValidatableResponse response){
        String message = response
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");
        assertEquals(message,"User already exists");
    }

    public void checkBadRequestCreateForbidden(ValidatableResponse response){
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

    public void checkBadRequestLoginUnauthorized(ValidatableResponse loginResponse){
        String message = loginResponse
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
        assertEquals(message,"email or password are incorrect");

    }

    public void deleteUser(ValidatableResponse loginResponse){
        String message = loginResponse
                .assertThat()
                .statusCode(202)
                .extract()
                .path("message");
        assertEquals(message,"User successfully removed");
    }

    public void checkGetUserData(ValidatableResponse editResponse){
        boolean success = editResponse
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
        assertTrue(success);
    }
    public void checkEditUser(ValidatableResponse editResponse){
        boolean success = editResponse
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
        assertTrue(success);
    }
    public void checkBadRequestEditUnauthorized(ValidatableResponse editResponse){
        String message = editResponse
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
        assertEquals(message,"You should be authorised");
    }

    public void checkDuplicateEmailForbiddenEdit(ValidatableResponse editResponse){
        String message = editResponse
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");
        assertEquals(message,"User with such email already exists");
    }
}
