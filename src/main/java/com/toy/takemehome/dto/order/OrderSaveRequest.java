package com.toy.takemehome.dto.order;

import com.toy.takemehome.dto.delivery.DeliveryOrderRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSaveRequest {

    private Long customerId;
    private Long restaurantId;
    private Long riderId;
    private OrderMenusRequest orderMenusRequest;
    private DeliveryOrderRequest deliveryOrderRequest;

    public OrderSaveRequest(Long customerId, Long restaurantId, Long riderId,
                            OrderMenusRequest orderMenusRequest, DeliveryOrderRequest deliveryOrderRequest) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.riderId = riderId;
        this.orderMenusRequest = orderMenusRequest;
        this.deliveryOrderRequest = deliveryOrderRequest;
    }
}
