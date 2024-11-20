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

public class OrderCreateTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    private OrderClient orderClient = new OrderClient();
    private OrderChecks ordercheck = new OrderChecks();
    private String accessToken;
    private ArrayList<String> id;

    @After
    public void deleteUser(){
        if(accessToken != null){
            ValidatableResponse response = client.deleteUser(accessToken);
            check.deleteUser(response);
        }
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами авторизованным пользователем")
    public void successAuthorizedUserCreateOrder(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        ValidatableResponse dataResponse = orderClient.getAllIngredients();
        id = ordercheck.checkGetIngredients(dataResponse);

        var order = new Order(id);
        ValidatableResponse orderResponse = orderClient.createAuthorizedUserOrder(order,accessToken);
        ordercheck.checkCreated(orderResponse);
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами неавторизованным пользователем")
    public void successUnauthorizedUserCreateOrder(){
        ValidatableResponse dataResponse = orderClient.getAllIngredients();
        id = ordercheck.checkGetIngredients(dataResponse);

        var order = new Order(id);
        ValidatableResponse orderResponse = orderClient.createUnauthorizedUserOrder(order);
        ordercheck.checkCreated(orderResponse);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void failedWithoutIngredientsCreateOrder(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        var order = Order.orderWithoutIngredients();
        ValidatableResponse orderResponse = orderClient.createAuthorizedUserOrder(order,accessToken);
        ordercheck.checkBadRequestCreated(orderResponse);
    }

    @Test
    @DisplayName("Создание заказа с несуществующими ингредиентами")
    public void failedWithWrongIngredientsCreateOrder(){
        var user = User.randomUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        var creds = UserCredentionals.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.checkLoggedIn(loginResponse);

        var order = Order.orderWithWrongIngredients();
        ValidatableResponse orderResponse = orderClient.createAuthorizedUserOrder(order,accessToken);
        ordercheck.checkCreatedWithWrongIngredients(orderResponse);
    }
}
