package com.yandex;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
@Builder

public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private int track;
    private int id;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color, int track, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        this.track = track;
        this.id = id;
    }

    public static Order getRandomOrder() {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final String metroStation = RandomStringUtils.randomAlphabetic(10);
        final String phone = RandomStringUtils.randomAlphabetic(10);
        final String rentTime = RandomStringUtils.randomNumeric(2);
        final String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        final String comment = RandomStringUtils.randomAlphabetic(10);
        final String[] color = {};
        final int track = 0;
        final int id = 0;

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color, track, id);
    }

}
