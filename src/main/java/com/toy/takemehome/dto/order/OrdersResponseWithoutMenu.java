package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrdersResponseWithoutMenu {
    private List<OrderResponseWithoutMenu> ordersResponseWithoutMenu;

    public OrdersResponseWithoutMenu(List<Order> orders) {
        this.ordersResponseWithoutMenu = create(orders);
    }

    private List<OrderResponseWithoutMenu> create(List<Order> orders) {
        return orders.stream()
                .map(OrderResponseWithoutMenu::new)
                .collect(Collectors.toList());
    }
}
