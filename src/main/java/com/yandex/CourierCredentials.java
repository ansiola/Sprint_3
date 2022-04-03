package com.yandex;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourierCredentials {

    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials(Courier courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
    }
}
