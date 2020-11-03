package com.toy.takemehome.dto.delivery;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryOrderRequest {

    private double distance;
    private int price;

    public DeliveryOrderRequest(double distance, int price) {
        this.distance = distance;
        this.price = price;
    }
}
