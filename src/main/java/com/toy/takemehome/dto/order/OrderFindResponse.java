package com.toy.takemehome.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import com.toy.takemehome.dto.menu.MenuNameCount;
import com.toy.takemehome.dto.menu.MenuNameCounts;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.order.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderFindResponse {

    private OrderCustomer orderCustomer;
    private OrderDelivery orderDelivery;
    private OrderRestaurant orderRestaurant;
    private OrderRider orderRider;
    private OrderStatus orderStatus;
    private MenuNameCounts menuNameCounts;

    @QueryProjection
    public OrderFindResponse(Order order, List<OrderMenu> orderMenus) {
        if (order.isAssigned()) {
            this.orderRider = new OrderRider(order.getRider());
        }
        this.orderCustomer = new OrderCustomer(order.getCustomer());
        this.orderDelivery = new OrderDelivery(order.getDelivery());
        this.orderRestaurant = new OrderRestaurant(order.getRestaurant());
        this.orderStatus = order.getStatus();
        this.menuNameCounts = new MenuNameCounts(createMenuNameCounts(orderMenus));
    }

    private List<MenuNameCount> createMenuNameCounts(List<OrderMenu> orderMenus) {
        return orderMenus.stream()
                .map(orderMenu -> new MenuNameCount(orderMenu.getMenu().getName(), orderMenu.getCount()))
                .collect(Collectors.toList());
    }
}
