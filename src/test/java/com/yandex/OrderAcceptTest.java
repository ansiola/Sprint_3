package com.yandex;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class OrderAcceptTest {
    ScooterClient scooterClient;
    int courierId = 0;
    int orderId = 0;
    int track = 0;

    @Before
    public void setUp() {
        scooterClient = new ScooterClient();
    }

    @Test
    @DisplayName("Check put accept order with valid data")
    public void checkPutAcceptOrderWithValidData() {
        courierId = scooterClient.createRandomCourier();
        HashMap<String, Integer> TrackAndIdOrder = scooterClient.createRandomOrder();
        orderId = TrackAndIdOrder.get("id");
        track = TrackAndIdOrder.get("track");

        ValidatableResponse acceptOrderResponse = scooterClient.putAcceptOrder(orderId, courierId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        boolean message = acceptOrderResponse.extract().path("ok");
        Assert.assertEquals("Не верный статус-код", 200, statusCode);
        Assert.assertTrue("Не верное ответное сообщение сервера", message);
    }

    @Test
    @DisplayName("Check put accept order with not valid courier id")
    public void checkPutAcceptOrderWithNotValidCourierId() {
        courierId = scooterClient.createRandomCourier();
        //удалим курьера чтобы он не существовал
        scooterClient.deleteCourier(courierId);
        HashMap<String, Integer> TrackAndIdOrder = scooterClient.createRandomOrder();
        orderId = TrackAndIdOrder.get("id");
        track = TrackAndIdOrder.get("track");

        ValidatableResponse acceptOrderResponse = scooterClient.putAcceptOrder(orderId, courierId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        String message = acceptOrderResponse.extract().path("message");
        Assert.assertEquals("Не верный статус-код", 404, statusCode);
        Assert.assertEquals("Не верное сообщение об ошибке", "Курьера с таким id не существует", message);
    }

    @Test
    @DisplayName("Check put accept order without courier id")
    public void checkPutAcceptOrderWithoutCourierId() {
        courierId = 0;
        HashMap<String, Integer> TrackAndIdOrder = scooterClient.createRandomOrder();
        orderId = TrackAndIdOrder.get("id");
        track = TrackAndIdOrder.get("track");

        ValidatableResponse acceptOrderResponse = scooterClient.putAcceptOrder(orderId, courierId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        String message = acceptOrderResponse.extract().path("message");
        Assert.assertEquals("Не верный статус-код", 400, statusCode);
        Assert.assertEquals("Не верное сообщение об ошибке", "Недостаточно данных для поиска", message);
    }

    @Test
    @DisplayName("Check put accept order without order id")
    public void checkPutAcceptOrderWithoutOrderId() {
        courierId = scooterClient.createRandomCourier();
        orderId = 0;
        track = 0;

        ValidatableResponse acceptOrderResponse = scooterClient.putAcceptOrder(orderId, courierId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        String message = acceptOrderResponse.extract().path("message");
        Assert.assertEquals("Не верный статус-код", 404, statusCode);
        Assert.assertEquals("Не верное сообщение об ошибке", "Not Found.", message);
    }

    @Test
    @DisplayName("Check put accept order with not order id")
    public void checkPutAcceptOrderWithNotValidOrderId() {
        courierId = scooterClient.createRandomCourier();
        orderId = 999999;
        track = 0;
        ValidatableResponse acceptOrderResponse = scooterClient.putAcceptOrder(orderId, courierId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        String message = acceptOrderResponse.extract().path("message");
        Assert.assertEquals("Не верный статус-код", 400, statusCode);
        Assert.assertEquals("Не верное сообщение об ошибке", "Недостаточно данных для поиска", message);
    }

    @After
    public void tearDown() {
        scooterClient.deleteCourier(courierId);
        scooterClient.cancelOrderByTrack(track);
    }
}
