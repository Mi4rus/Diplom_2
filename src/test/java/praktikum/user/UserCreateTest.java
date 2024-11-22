package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserCreateTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();

    String accessToken;

    @After
    public void deleteUser(){
        if(accessToken != null){
            ValidatableResponse response = client.deleteUser(accessToken);
            check.deleteUser(response);
        }
    }

    @Test
    @DisplayName("Создание пользователя")
    public void successCreateUserTest(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        // авторизация для удаления пользователя
        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("Невозможно создать двух одинаковых пользователей")
    public void failedDuplicateCreateUserTest(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        ValidatableResponse duplicateResponse = client.createUser(user);
        check.checkDuplicateCreateForbidden(duplicateResponse);

    }

    @Test
    @DisplayName("Невозможно создать пользователя без email")
    public void failedBadRequestWithoutEmailCreateUserTest(){
        var user = User.userWithoutEmail();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestCreateForbidden(createResponse);
    }
    @Test
    @DisplayName("Невозможно создать пользователя c пустым email")
    public void failedBadRequestWithEmptyEmailCreateUserTest(){
        var user = User.userWithEmptyEmail();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestCreateForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без password")
    public void failedBadRequestWithoutPasswordCreateUserTest(){
        var user = User.userWithoutPassword();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestCreateForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя c пустым password")
    public void failedBadRequestWithEmptyPasswordCreateUserTest(){
        var user = User.userWithEmptyPassword();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestCreateForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без name")
    public void failedBadRequestWithoutNameCreateUserTest(){
        var user = User.userWithoutName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestCreateForbidden(createResponse);
    }

    @Test
    @DisplayName("Невозможно создать пользователя c пустым name")
    public void failedBadRequestWithEmptyNameCreateUserTest(){
        var user = User.userWithEmptyName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkBadRequestCreateForbidden(createResponse);
    }
}
