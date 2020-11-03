package com.toy.takemehome.dto.order;

import com.toy.takemehome.dto.delivery.DeliveryOrderRequest;
import com.toy.takemehome.dto.menu.MenuIdCounts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSaveRequest {

    private Long customerId;
    private Long restaurantId;
    private MenuIdCounts menuIdCounts;
    private DeliveryOrderRequest deliveryOrderRequest;

    public OrderSaveRequest(Long customerId, Long restaurantId,
                            MenuIdCounts menuIdCounts, DeliveryOrderRequest deliveryOrderRequest) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.menuIdCounts = menuIdCounts;
        this.deliveryOrderRequest = deliveryOrderRequest;
    }
}
