package com.toy.takemehome.dto.order;

import com.toy.takemehome.dto.menu.MenuIdCounts;
import com.toy.takemehome.entity.order.PaymentStatus;
import com.toy.takemehome.entity.order.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class OrderSaveRequest {

    private Long customerId;
    private Long restaurantId;

    @NotEmpty
    private MenuIdCounts menuIdCounts;

    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private int totalPrice;
    private int requiredTime;

    public OrderSaveRequest(Long customerId, Long restaurantId, MenuIdCounts menuIdCounts, PaymentType paymentType,
                            PaymentStatus paymentStatus, int totalPrice, int requiredTime) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.menuIdCounts = menuIdCounts;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
        this.requiredTime = requiredTime;
    }
}
