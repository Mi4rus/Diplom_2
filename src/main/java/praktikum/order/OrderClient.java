package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends praktikum.Client{
    private static final String ORDER_PATH = "/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder (Order order, String token){
        return spec()
                .header("Authorization",token)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }


}
