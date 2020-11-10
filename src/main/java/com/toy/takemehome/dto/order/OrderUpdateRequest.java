package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.order.OrderStatus;
import com.toy.takemehome.entity.order.PaymentStatus;
import com.toy.takemehome.entity.order.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderUpdateRequest {

    private OrderDelivery orderDelivery;
    private String customerName;
    private OrderRider orderRider;
    private OrderStatus orderStatus;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private int totalPrice;

    public OrderUpdateRequest(OrderDelivery orderDelivery, String customerName, OrderRider orderRider,
                              OrderStatus orderStatus, PaymentType paymentType, PaymentStatus paymentStatus, int totalPrice) {
        this.orderDelivery = orderDelivery;
        this.customerName = customerName;
        this.orderRider = orderRider;
        this.orderStatus = orderStatus;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
    }
}
