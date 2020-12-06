package com.toy.takemehome.entity.order;

import com.toy.takemehome.dto.order.*;
import com.toy.takemehome.entity.BaseTimeEntity;
import com.toy.takemehome.entity.Location;
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
@EqualsAndHashCode(of = {"id"}, callSuper = false)
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

    private int totalPrice;
    private int requiredTime;
    private int cookingTime;

    @Builder
    public Order(Long id, Customer customer, Restaurant restaurant, Rider rider, Delivery delivery, OrderStatus status,
                 PaymentType paymentType, PaymentStatus paymentStatus, int totalPrice, int requiredTime, int cookingTime) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.rider = rider;
        this.delivery = delivery;
        this.status = status;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
        this.requiredTime = requiredTime;
        this.cookingTime = cookingTime;
    }

    public static Order createOrder(Customer customer, Restaurant restaurant, Delivery delivery, OrderStatus orderStatus,
                                    PaymentType paymentType, PaymentStatus paymentStatus, int totalPrice, int requiredTime) {
        final Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .delivery(delivery)
                .status(orderStatus)
                .paymentType(paymentType)
                .paymentStatus(paymentStatus)
                .totalPrice(totalPrice)
                .requiredTime(requiredTime)
                .build();

        return order;
    }

    public void update(OrderUpdateRequest updateRequest) {
        checkAssignedRider();
        checkPositiveOrZeroNumber(updateRequest.getTotalPrice());
        this.customer.changePhoneNumber(updateRequest.getCustomerName());

        final OrderRider orderRider = updateRequest.getOrderRider();
        this.rider.changeNamePhoneNumber(orderRider.getName(), orderRider.getPhoneNumber());

        final OrderDelivery orderDelivery = updateRequest.getOrderDelivery();
        this.delivery.changeAll(orderDelivery.getPrice(), orderDelivery.getDistance(),
                orderDelivery.getAddress(), orderDelivery.getStatus());
        this.status = updateRequest.getOrderStatus();
        this.paymentType = updateRequest.getPaymentType();
        this.paymentStatus = updateRequest.getPaymentStatus();
        this.totalPrice = updateRequest.getTotalPrice();
    }

    public void assigned(Rider rider) {
        this.rider = rider;
        this.delivery.assigned();
    }

    public boolean isAssigned() {
        return this.delivery.isAssigned();
    }

    public boolean isNotAssigned() {
        return this.delivery.isNotAssigned();
    }

    public void cancel() {
        this.status = CANCEL;
        delivery.cancel();
    }

    public double getTotalDistance(Location location) {
        return restaurant.calculateDistance(location) + customer.calculateDistance(location);
    }

    public double calculateLocation(Location location) {
        return location.calculateDistance(location);
    }

    public void requestDelivery(int cookingTime) {
        delivery.request();
        this.cookingTime = cookingTime;
    }

    public void complete() {
        if (this.delivery.isNotPickup()) {
            throw new IllegalArgumentException(
                    String.format("current delivery status: %s, delivery status must pickup", this.delivery.getStatus()));
        }
        this.status = COMPLETE;
        this.delivery.complete();
    }

    public void pickup() {
        this.delivery.pickup();
    }

    public void reception() {
        this.status = RECEPTION;
    }

    public void changeRequiredTime(int requiredTime) {
        this.requiredTime = requiredTime;
    }

    private boolean notAssignedRider() {
        return !isAssigned();
    }

    private void checkAssignedRider() {
        if (notAssignedRider()) {
            throw new IllegalArgumentException(String.format("current order id: %d, not assigned rider!", this.id));
        }
    }

    private void checkPositiveOrZeroNumber(int number) {
        if (number < 0) {
            throw new IllegalArgumentException(String.format("input number %d, must positive or zero number!", number));
        }
    }
}
