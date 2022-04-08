package com.yandex;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final String[] colorArray;
    ScooterClient scooterClient;
    int orderId;

    public OrderCreateTest(String[] colorArray) {
        this.colorArray = colorArray;
    }

    @Parameterized.Parameters(name = "Test scooter color: {0}{1}")
    public static Object[][] colorArrayData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null},
        };
    }

    @Test
    @DisplayName("Check new order can create with valid date and different colors")
    public void newOrderCanCreateWithValidDataAndDifferentColors() {
        scooterClient = new ScooterClient();
        Order orderWithValidData = Order.getRandomOrder();
        orderWithValidData.setColor(colorArray);
        ValidatableResponse createResponse = scooterClient.createOrder(orderWithValidData);
        int statusCode = createResponse.extract().statusCode();
        if (statusCode == 201) {
            orderId = createResponse.extract().path("track");
        } else {
            orderId = 0;
        }
        Assert.assertEquals("Не верный статус-код", 201, statusCode);
        Assert.assertNotEquals("Не удалось создать заказ", 0, orderId);
    }
}
