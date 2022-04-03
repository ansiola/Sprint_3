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

    @Parameterized.Parameters
    public static Object[][] colorArrayData() {
        String[] colorBlack = {"BLACK"};
        String[] colorGrey = {"GREY"};
        String[] colorAll = {"BLACK", "GREY"};
        String[] colorEmpty = {};
        return new Object[][]{
                {colorBlack},
                {colorGrey},
                {colorAll},
                {colorEmpty},
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

        Assert.assertNotEquals("Не удалось создать заказ", 0, orderId);
        Assert.assertEquals("Не верный статус-код", 201, statusCode);
    }
}
