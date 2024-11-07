package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends praktikum.Client{
    private static final String ORDER_PATH = "/orders";

    @Step("Создание заказа")
    public ValidatableResponse createAuthorizedUserOrder (Order order, String token){
        return spec()
                .header("Authorization",token)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Создание заказа")
    public ValidatableResponse createUnauthorizedUserOrder (Order order){
        return spec()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Получение заказов пользователя с авторизацией")
    public ValidatableResponse getOrdersAuthorizedUserOrder (String token){
        return spec()
                .header("Authorization",token)
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
    @Step("Получение заказов пользователя с авторизацией")
    public ValidatableResponse getOrdersUnauthorizedUserOrder (){
        return spec()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
}
