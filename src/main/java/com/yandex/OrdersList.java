package com.yandex;

import lombok.Data;

import java.util.List;

@Data
public class OrdersList {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<AvailableStation> availableStations;

    public OrdersList(List<Order> orders, PageInfo pageInfo, List<AvailableStation> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }
}
