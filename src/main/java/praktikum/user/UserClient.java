package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends praktikum.Client{
    private static final String USER_PATH = "/auth";


    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user){
        return spec()
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then().log().all();

    }

    @Step("Авторизация пользователем")
    public ValidatableResponse loginUser(UserCredentionals creds){
        return spec()
                .body(creds)
                .when()
                .post(USER_PATH + "/login")
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String token){
        return spec()
                .header("Authorization",token)
                .when()
                .delete(USER_PATH + "/user")
                .then().log().all();
    }

}
