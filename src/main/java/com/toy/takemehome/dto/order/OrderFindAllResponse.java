package com.toy.takemehome.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderFindAllResponse {

    private List<OrderFindResponse> orderFindResponses;

    public OrderFindAllResponse(List<OrderFindResponse> orderFindResponses) {
        this.orderFindResponses = orderFindResponses;
    }
}
