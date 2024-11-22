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

        var data = UserData.fromChangeDataUser(user);
        ValidatableResponse editResponse = client.editUser(accessToken, data);
        check.checkEditUser(editResponse);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void failedUnauthorizedEditUserTest(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var data = UserData.fromChangeDataUser(user);
        ValidatableResponse editResponse = client.editUserUnauthorized(data);
        check.checkBadRequestEditUnauthorized(editResponse);
    }

    @Test
    @DisplayName("Невозможно изменить почту на существующую")
    public void failedChangeEmailEditUserTest(){
        var user1 = User.randomUser();
        var user2 = User.randomUser();

        ValidatableResponse createResponse1 = client.createUser(user1);
        check.checkCreated(createResponse1);
        ValidatableResponse createResponse2 = client.createUser(user2);
        check.checkCreated(createResponse2);

        var creds2 = UserCredentionals.fromUser(user2);
        ValidatableResponse loginResponse2= client.loginUser(creds2);
        accessToken = check.checkLoggedIn(loginResponse2);

        var data = UserData.dataUser(user1);
        ValidatableResponse editResponse = client.editDuplicateEmailUser(accessToken,data);
        check.checkDuplicateEmailForbiddenEdit(editResponse);
    }


}
