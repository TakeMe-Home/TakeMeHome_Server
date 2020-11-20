package com.toy.takemehome.dto.delivery;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryPrice {

    private int price;

    public DeliveryPrice(int price) {
        this.price = price;
    }
}
