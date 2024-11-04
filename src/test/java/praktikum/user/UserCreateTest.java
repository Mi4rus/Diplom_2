package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserCreateTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();

    String accessToken;
    String refreshToken;

    @After
    public void deleteUser(){
    }

    @Test
    @DisplayName("Создание пользователя")
    public void successCreateUserTest(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать двух одинаковых пользователей")
    public void failedDuplicateCreateUserTest(){
        var user1 = User.randomUser();
        var user2 = user1;
        ValidatableResponse createResponse1 = client.createUser(user1);
        check.checkCreated(createResponse1);

        ValidatableResponse createResponse2 = client.createUser(user2);
        check.checkDuplicateForbidden(createResponse2);

    }

    @Test
    @DisplayName("Невозможно создать пользователя без email")
    public void failedBadRequestWithoutEmailCreateUserTest(){
        var user = User.userWithoutEmail();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestForbidden(createResponse);
    }
    @Test
    @DisplayName("Невозможно создать пользователя c пустым email")
    public void failedBadRequestWithEmptyEmailCreateUserTest(){
        var user = User.userWithEmptyEmail();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без password")
    public void failedBadRequestWithoutPasswordCreateUserTest(){
        var user = User.userWithoutPassword();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя c пустым password")
    public void failedBadRequestWithEmptyPasswordCreateUserTest(){
        var user = User.userWithEmptyPassword();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без name")
    public void failedBadRequestWithoutNameCreateUserTest(){
        var user = User.userWithoutName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя c пустым name")
    public void failedBadRequestWithEmptyNameCreateUserTest(){
        var user = User.userWithEmptyName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestForbidden(createResponse);
    }
}
