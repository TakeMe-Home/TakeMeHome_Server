package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderFindAllRequestStatusResponse {

    List<OrderFindRequestStatusResponse> orderFindRequestStatusResponses;

    public OrderFindAllRequestStatusResponse(List<Order> orders) {
        this.orderFindRequestStatusResponses = createOrderFindAll(orders);
    }

    private List<OrderFindRequestStatusResponse> createOrderFindAll(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderFindRequestStatusResponse(order))
                .collect(Collectors.toList());
    }
}
