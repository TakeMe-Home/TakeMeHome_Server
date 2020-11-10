package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderStatus;
import com.toy.takemehome.entity.order.PaymentStatus;
import com.toy.takemehome.entity.order.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseWithoutMenu {

    private OrderCustomer orderCustomer;
    private OrderDelivery orderDelivery;
    private OrderRestaurant orderRestaurant;
    private OrderRider orderRider;
    private OrderStatus orderStatus;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private int totalPrice;

    public OrderResponseWithoutMenu(Order order) {
        if (order.isAssigned()) {
            this.orderRider = new OrderRider(order.getRider());
        }
        this.orderCustomer = new OrderCustomer(order.getCustomer());
        this.orderDelivery = new OrderDelivery(order.getDelivery());
        this.orderRestaurant = new OrderRestaurant(order.getRestaurant());
        this.orderStatus = order.getStatus();
        this.paymentType = order.getPaymentType();
        this.paymentStatus = order.getPaymentStatus();
        this.totalPrice = order.getTotalPrice();
    }
}
