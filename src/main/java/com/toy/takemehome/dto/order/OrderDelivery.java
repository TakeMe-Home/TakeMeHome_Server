package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.delivery.Delivery;
import com.toy.takemehome.entity.delivery.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDelivery {

    private int price;
    private double distance;
    private String address;
    private DeliveryStatus status;

    public OrderDelivery(Delivery delivery) {
        this.price = delivery.getPrice();
        this.distance = delivery.getDistance();
        this.address = delivery.getAddress();
        this.status = delivery.getStatus();
    }
}
