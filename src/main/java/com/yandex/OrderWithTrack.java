package com.yandex;

import lombok.Data;

@Data

public class OrderWithTrack {
    private Order order;

    public OrderWithTrack(Order order) {
        this.order = order;
    }

}
