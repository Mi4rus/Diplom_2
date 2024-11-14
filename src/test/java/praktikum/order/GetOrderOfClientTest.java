package praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;
import praktikum.user.UserCredentionals;

import java.util.ArrayList;

public class GetOrderOfClientTest {

    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    private OrderClient orderClient = new OrderClient();
    private OrderChecks ordercheck = new OrderChecks();
    private String accessToken;
    private ArrayList<String> _id;

    @After
    public void deleteUser(){
        if(accessToken != null){
            ValidatableResponse response = client.deleteUser(accessToken);
            check.deleteUser(response);
        }
    }

    @Test
    @DisplayName("Получение заказов пользователем с авторизацией")
    public void successAuthorizedUserGetOrder(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        ValidatableResponse dataResponse = orderClient.getAllIngredients();
        _id = ordercheck.checkGetIngredients(dataResponse);

        var order = new Order(_id);
        ValidatableResponse orderResponse = orderClient.createAuthorizedUserOrder(order,accessToken);
        ordercheck.checkCreated(orderResponse);

        ValidatableResponse getOrderResponse = orderClient.getOrdersAuthorizedUserOrder(accessToken);
        ordercheck.checkGetOrdersWithAuthorization(getOrderResponse);
    }

    @Test
    @DisplayName("Получение заказов пользователем без авторизации")
    public void failedBadRequestUnauthorizedUserGetOrder(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        ValidatableResponse dataResponse = orderClient.getAllIngredients();
        _id = ordercheck.checkGetIngredients(dataResponse);

        var order = new Order(_id);
        ValidatableResponse orderResponse = orderClient.createAuthorizedUserOrder(order,accessToken);
        ordercheck.checkCreated(orderResponse);

        ValidatableResponse getOrderResponse = orderClient.getOrdersUnauthorizedUserOrder();
        ordercheck.checkGetOrdersWithoutAuthorization(getOrderResponse);
    }
}
