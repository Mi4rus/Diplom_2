package praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;
import praktikum.user.UserCredentionals;

public class OrderCreateTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    private OrderClient orderClient = new OrderClient();
    private OrderChecks ordercheck = new OrderChecks();
    String accessToken;

    @After
    public void deleteUser(){
        if(accessToken != null){
            ValidatableResponse response = client.deleteUser(accessToken);
            check.deleteUser(response);
        }
    }

    @Test
    @DisplayName("Создание заказа с ингридиентами авторизованным пользователем")
    public void successCreateOrder(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        var order = Order.orderWithIngredients();
        ValidatableResponse orderResponse = orderClient.createOrder(order,accessToken);
        ordercheck.checkCreated(orderResponse);
    }
}
