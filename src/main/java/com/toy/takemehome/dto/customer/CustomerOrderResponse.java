package com.toy.takemehome.dto.customer;

import com.toy.takemehome.dto.menu.MenuNameCounts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerOrderResponse {

    private MenuNameCounts menuNameCounts;
    private int totalPrice;
    private String customerAddress;

    public CustomerOrderResponse(MenuNameCounts menuNameCounts, int totalPrice, String customerAddress) {
        this.menuNameCounts = menuNameCounts;
        this.totalPrice = totalPrice;
        this.customerAddress = customerAddress;
    }
}
