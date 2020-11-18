package com.toy.takemehome.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDeliveryRequest {

    private int cookingTime;

    public OrderDeliveryRequest(int cookingTime) {
        this.cookingTime = cookingTime;
    }
}
