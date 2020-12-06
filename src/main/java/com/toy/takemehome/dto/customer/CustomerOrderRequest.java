package com.toy.takemehome.dto.customer;

import com.toy.takemehome.dto.menu.MenuNameCounts;
import com.toy.takemehome.entity.order.PaymentStatus;
import com.toy.takemehome.entity.order.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CustomerOrderRequest {
    private Long customerId;
    private Long restaurantId;

    @NotEmpty
    private MenuNameCounts menuNameCounts;

    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private int totalPrice;

    public CustomerOrderRequest(Long customerId, Long restaurantId, @NotEmpty MenuNameCounts menuNameCounts,
                                PaymentType paymentType, PaymentStatus paymentStatus, int totalPrice) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.menuNameCounts = menuNameCounts;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
    }
}
