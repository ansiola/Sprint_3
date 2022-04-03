package com.yandex;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CourierCreateTest {

    ScooterClient scooterClient;
    Courier courierWithValidData;
    int courierId;

    @Before
    public void setUp() {
        scooterClient = new ScooterClient();
      }

    @Test
    @DisplayName("Check new courier can create with valid date")

    public void courierCreateWithValidCredentials() {
        courierWithValidData = Courier.getRandomCourier();
        CourierCredentials courierCredentialsWithValidData = new CourierCredentials(courierWithValidData);

        ValidatableResponse createResponse = scooterClient.createCourier(courierWithValidData);
        courierId = scooterClient.getIdCourier(courierCredentialsWithValidData);
        int statusCode = createResponse.extract().statusCode();
        boolean message = createResponse.extract().path("ok");

        Assert.assertNotEquals("Не удалось создать курьера", 0, courierId);
        Assert.assertTrue("Не верное сообщение об ошибке", message);
        Assert.assertEquals("Не верный статус-код", 201, statusCode);
    }

    @Test
    @DisplayName("Check new courier can't create without password")

    public void courierCreateCannotWithoutPassword() {
        Courier courierWithoutPassword = Courier.builder()
                .login("ValidTestLogin")
                .firstName("ValidName")
                .build();
        ValidatableResponse createResponse = scooterClient.createCourier(courierWithoutPassword);
        int statusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");
        Assert.assertEquals("Не верное сообщение об ошибке", "Недостаточно данных для создания учетной записи", message);
        Assert.assertEquals("Не верный статус-код", 400, statusCode);
    }

    @Test
    @DisplayName("Check new courier can't create without login")
    public void courierCreateCannotWithoutLogin() {
        Courier courierWithoutLogin = Courier.builder()
                .password("ValidPassword")
                .firstName("ValidName")
                .build();
        ValidatableResponse createResponse = scooterClient.createCourier(courierWithoutLogin);
        int statusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");
        Assert.assertEquals("Не верное сообщение об ошибке", "Недостаточно данных для создания учетной записи", message);
        Assert.assertEquals("Не верный статус-код", 400, statusCode);
    }

    @Test
    @DisplayName("Check new courier can create without first name")
    public void courierCreateCanWithoutFirstName() {
        Courier courierWithoutFirstName = Courier.builder()
                .login("ValidTestLogin")
                .password("ValidPassword")
                .build();
        CourierCredentials courierCredentialsWithoutFirstName = new CourierCredentials(courierWithoutFirstName);

        ValidatableResponse createResponse = scooterClient.createCourier(courierWithoutFirstName);
        courierId = scooterClient.getIdCourier(courierCredentialsWithoutFirstName);
        int statusCode = createResponse.extract().statusCode();
        boolean message = createResponse.extract().path("ok");
        Assert.assertNotEquals("Не удалось создать курьера", 0, courierId);
        Assert.assertTrue("Не верное сообщение об ошибке", message);
        Assert.assertEquals("Не верный статус-код", 201, statusCode);
    }

    @Test
    @DisplayName("Check  can't create two identical courier")
    public void createCannotTwoIdenticalCouriers() {
        courierWithValidData = Courier.getRandomCourier();

        ValidatableResponse createFirstCourierResponse = scooterClient.createCourier(courierWithValidData);
        ValidatableResponse createSecondCourierResponse = scooterClient.createCourier(courierWithValidData);
        int statusCode = createSecondCourierResponse.extract().statusCode();
        String message = createSecondCourierResponse.extract().path("message");

        Assert.assertEquals("Не верное сообщение об ошибке", "Этот логин уже используется. Попробуйте другой.", message);
        Assert.assertEquals("Не верный статус-код", 409, statusCode);
    }

    @Test
    @DisplayName("Check new courier can't create with repeated login")
    public void createCannotCouriersWithRepeatedLogin() {
        courierWithValidData = Courier.getRandomCourier();
        Courier courierWithRepeatedLogin = new Courier(courierWithValidData.getLogin(), "newPassword", "Name");

        ValidatableResponse createFirstCourierResponse = scooterClient.createCourier(courierWithValidData);
        ValidatableResponse createSecondCourierResponse = scooterClient.createCourier(courierWithRepeatedLogin);
        int statusCode = createSecondCourierResponse.extract().statusCode();
        String message = createSecondCourierResponse.extract().path("message");

        Assert.assertEquals("Не верное сообщение об ошибке", "Этот логин уже используется. Попробуйте другой.", message);
        Assert.assertEquals("Не верный статус-код", 409, statusCode);
    }


    @After
    public void tearDown() {
        scooterClient.deleteCourier(courierId);
    }

}
