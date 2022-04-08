package com.yandex;

import io.qameta.allure.Allure;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@Builder

public class Courier {

    public String login;
    public String password;
    public String firstName;

    public Courier() {
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandomCourier() {
        final String courierRandomLogin = RandomStringUtils.randomAlphabetic(10);
        final String courierRandomPassword = RandomStringUtils.randomAlphabetic(10);
        final String courierRandomFirstName = RandomStringUtils.randomAlphabetic(10);

        Allure.addAttachment("Логин", courierRandomLogin);
        Allure.addAttachment("Пароль", courierRandomPassword);
        Allure.addAttachment("Имя", courierRandomPassword);

        return new Courier(courierRandomLogin, courierRandomPassword, courierRandomFirstName);
    }
}
