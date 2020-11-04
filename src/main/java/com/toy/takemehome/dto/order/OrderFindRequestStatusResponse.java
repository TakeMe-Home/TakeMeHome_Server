package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderFindRequestStatusResponse {

    private OrderCustomer orderCustomer;
    private OrderDelivery orderDelivery;
    private OrderRestaurant orderRestaurant;
    private OrderStatus orderStatus;

    public OrderFindRequestStatusResponse(Order order) {
        this.orderCustomer = new OrderCustomer(order.getCustomer());
        this.orderDelivery = new OrderDelivery(order.getDelivery());
        this.orderRestaurant = new OrderRestaurant(order.getRestaurant());
        this.orderStatus = order.getStatus();
    }
}
