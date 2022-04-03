package com.yandex;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderGetByTrackTest {
    ScooterClient scooterClient;
    int trackId;

    @Before
    public void setUp() {
        scooterClient = new ScooterClient();
    }

    @Test
    @DisplayName("Check get order with valid track id")
    public void checkGetOrderWithValidTrackId() {
        trackId = createRandomOrder();
        Response getOrderResponse = scooterClient.getOrderByTrack(trackId);
        Order order = getOrderResponse.body().as(OrderWithTrack.class).getOrder();
        int statusCode = getOrderResponse.then().extract().statusCode();

        Assert.assertEquals("Не верный статус-код", 200, statusCode);
        Assert.assertEquals("Возвращен не верный заказ", order.getTrack(), trackId);
    }

    @Test
    @DisplayName("Check get order with not valid track id")
    public void checkGetOrderWithNotValidTrackId() {
        int notValidTrackId = 1;
        Response getOrderResponse = scooterClient.getOrderByTrack(notValidTrackId);
        int statusCode = getOrderResponse.then().extract().statusCode();
        String message = getOrderResponse.then().extract().path("message");

        Assert.assertEquals("Не верный статус-код", 404, statusCode);
        Assert.assertEquals("Не верное сообщение об ошибке", "Заказ не найден", message);
    }

    @Test
    @DisplayName("Check get order without track id")
    public void checkGetOrderWithoutTrackId() {
        int trackId = 0;
        Response getOrderResponse = scooterClient.getOrderByTrack(trackId);
        int statusCode = getOrderResponse.then().extract().statusCode();
        String message = getOrderResponse.then().extract().path("message");

        Assert.assertEquals("Не верный статус-код", 400, statusCode);
        Assert.assertEquals("Не верное сообщение об ошибке", "Недостаточно данных для поиска", message);
    }

    private int createRandomOrder() {
        Order orderWithValidData = Order.getRandomOrder();
        trackId = scooterClient.createOrder(orderWithValidData).extract().path("track");
        return trackId;
    }
}
