package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserLoginTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();

    String accessToken;
    String refreshToken;

    @After
    public void deleteUser(){
        if(accessToken != null){
            ValidatableResponse response = client.deleteUser(accessToken);
            check.deleteUser(response);
        }
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void successLoginUserTest(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);
    }
    @Test
    @DisplayName("Невозможно авторизоваться с пустым email")
    public void failedBadRequestWithEmptyEmailLoginTest(){
        var user = User.userWithEmptyEmail();

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        check.checkBadRequestUnauthorized(loginResponse);
    }
    @Test
    @DisplayName("Невозможно авторизоваться без email")
    public void failedBadRequestWithoutEmailLoginTest(){
        var user = User.userWithoutEmail();

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        check.checkBadRequestUnauthorized(loginResponse);
    }

    @Test
    @DisplayName("Невозможно авторизоваться c пустым password")
    public void failedBadRequestWithEmptyPasswordLoginTest(){
        var user = User.userWithEmptyPassword();

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        check.checkBadRequestUnauthorized(loginResponse);
    }

    @Test
    @DisplayName("Невозможно авторизоваться без password")
    public void failedBadRequestWithoutPasswordLoginTest(){
        var user = User.userWithoutPassword();

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        check.checkBadRequestUnauthorized(loginResponse);
    }

    @Test
    @DisplayName("Невозможно авторизоваться с несуществующей парой email-password")
    public void failedNotFoundWithNonExistentData(){
         var user = User.randomUser();

         ValidatableResponse createResponse = client.createUser(user);
         check.checkCreated(createResponse);

         var creds = UserCredentionals.fromNonExistentUser(user);
         ValidatableResponse loginResponse = client.loginUser(creds);
         check.checkBadRequestUnauthorized(loginResponse);
    }

}
