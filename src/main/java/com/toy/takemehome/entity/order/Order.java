package com.toy.takemehome.entity.order;

import com.toy.takemehome.dto.order.*;
import com.toy.takemehome.entity.BaseTimeEntity;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.delivery.Delivery;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;
import lombok.*;

import javax.persistence.*;

import static com.toy.takemehome.entity.order.OrderStatus.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private long totalPrice;

    @Builder
    public Order(Long id, Customer customer, Restaurant restaurant, Rider rider,
                 Delivery delivery, OrderStatus status, long totalPrice) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.rider = rider;
        this.delivery = delivery;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public static Order createOrder(Customer customer, Restaurant restaurant, Delivery delivery) {
        final Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .delivery(delivery)
                .status(ORDER)
                .build();

        return order;
    }

    public void update(OrderUpdateRequest updateRequest) {
        checkAssignedRider();
        this.customer.changePhoneNumber(updateRequest.getCustomerName());

        final OrderRider orderRider = updateRequest.getOrderRider();
        this.rider.changeNamePhoneNumber(orderRider.getName(), orderRider.getPhoneNumber());

        final OrderDelivery orderDelivery = updateRequest.getOrderDelivery();
        this.delivery.changeAll(orderDelivery.getPrice(), orderDelivery.getDistance(),
                orderDelivery.getAddress(), orderDelivery.getStatus());
        this.status = updateRequest.getOrderStatus();
    }

    public void assigned(Rider rider) {
        this.rider = rider;
        this.delivery.assigned();
    }

    public boolean isAssigned() {
        return this.delivery.isAssigned();
    }

    public void cancel() {
        this.status = CANCEL;
        delivery.cancel();
    }

    public void requestDelivery() {
        delivery.request();
    }

    private boolean notAssignedRider() {
        return !isAssigned();
    }

    private void checkAssignedRider() {
        if (notAssignedRider()) {
            throw new IllegalArgumentException(String.format("current order id: %d, not assigned rider!", this.id));
        }
    }
}
