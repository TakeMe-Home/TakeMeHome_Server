package com.toy.takemehome.dto.customer;

import com.toy.takemehome.dto.menu.MenuIdCounts;
import com.toy.takemehome.dto.menu.MenuNameCounts;
import com.toy.takemehome.entity.order.PaymentStatus;
import com.toy.takemehome.entity.order.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerOrderRequest {
    private Long restaurantId;
    private MenuNameCounts menuNameCounts;
    private int totalPrice;
    private String customerAddress;

    public CustomerOrderRequest(Long restaurantId, MenuNameCounts menuNameCounts, PaymentType paymentType,
                                PaymentStatus paymentStatus, int totalPrice, String customerAddress) {
        this.restaurantId = restaurantId;
        this.menuNameCounts = menuNameCounts;
        this.totalPrice = totalPrice;
        this.customerAddress = customerAddress;
    }
}
