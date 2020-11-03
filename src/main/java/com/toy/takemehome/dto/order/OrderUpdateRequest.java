package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.order.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderUpdateRequest {

    private OrderDelivery orderDelivery;
    private String customerName;
    private OrderRider orderRider;
    private OrderStatus orderStatus;

    public OrderUpdateRequest(OrderDelivery orderDelivery, String customerName, OrderRider orderRider, OrderStatus orderStatus) {
        this.orderDelivery = orderDelivery;
        this.customerName = customerName;
        this.orderRider = orderRider;
        this.orderStatus = orderStatus;
    }
}
