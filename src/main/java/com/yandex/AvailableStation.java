package com.yandex;

import lombok.Data;

@Data

public class AvailableStation {
    private String name;
    private int number;
    private String color;

    public AvailableStation(String name, int number, String color) {
        this.name = name;
        this.number = number;
        this.color = color;
    }
}
