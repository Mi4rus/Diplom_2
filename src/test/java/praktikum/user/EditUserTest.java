package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class EditUserTest {
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
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void successEditUserTest(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        ValidatableResponse getUserResponse = client.getUserData(accessToken);
        check.checkGetUserData(getUserResponse);

        ValidatableResponse editResponse = client.editUser(accessToken);
        check.checkEditUser(editResponse);
    }
}
