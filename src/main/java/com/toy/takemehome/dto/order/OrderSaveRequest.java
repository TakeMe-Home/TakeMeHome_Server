package com.toy.takemehome.dto.order;

import com.toy.takemehome.dto.delivery.DeliveryOrderRequest;
import com.toy.takemehome.dto.menu.MenuIdCounts;
import com.toy.takemehome.entity.order.PaymentStatus;
import com.toy.takemehome.entity.order.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSaveRequest {

    private Long customerId;
    private Long restaurantId;
    private MenuIdCounts menuIdCounts;
    private DeliveryOrderRequest deliveryOrderRequest;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private int totalPrice;

    public OrderSaveRequest(Long customerId, Long restaurantId, MenuIdCounts menuIdCounts,
                            DeliveryOrderRequest deliveryOrderRequest, PaymentType paymentType,
                            PaymentStatus paymentStatus, int totalPrice) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.menuIdCounts = menuIdCounts;
        this.deliveryOrderRequest = deliveryOrderRequest;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
    }
}
