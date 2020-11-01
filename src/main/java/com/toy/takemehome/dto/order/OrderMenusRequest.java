package com.toy.takemehome.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderMenusRequest {

    private List<OrderMenuRequest> orderMenusRequest;

    public OrderMenusRequest(List<OrderMenuRequest> orderMenusRequest) {
        this.orderMenusRequest = orderMenusRequest;
    }
}
