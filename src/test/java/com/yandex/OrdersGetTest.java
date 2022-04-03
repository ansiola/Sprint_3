package com.yandex;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class OrdersGetTest {
    ScooterClient scooterClient;

    @Before
    public void setUp() {
        scooterClient = new ScooterClient();
    }

    @Test
    @DisplayName("Check get all orders")
    public void checkGetAllOrders() {
        int totalOrder;
        Response ordersResponse = scooterClient.getOrders();
        OrdersList scooterOrders = ordersResponse.body().as(OrdersList.class);
        int statusCode = ordersResponse.then().extract().statusCode();

        if (statusCode == 200) {
            totalOrder = ordersResponse.then().extract().path("pageInfo.total");
        } else {
            totalOrder = 0;
        }
        assertThat(totalOrder, greaterThan(0));
        Assert.assertEquals("Не верный статус-код", 200, statusCode);
        Assert.assertFalse("Список заказов пустой", scooterOrders.getOrders().isEmpty());
    }
}
