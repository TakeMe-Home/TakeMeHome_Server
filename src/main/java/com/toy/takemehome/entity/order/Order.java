package com.toy.takemehome.entity.order;

import com.toy.takemehome.entity.BaseTimeEntity;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.delivery.Delivery;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;
import lombok.*;

import javax.persistence.*;

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

    @Builder
    public Order(Long id, Customer customer, Restaurant restaurant, Rider rider, Delivery delivery, OrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.rider = rider;
        this.delivery = delivery;
        this.status = status;
    }

    public static Order createOrder(Customer customer, Restaurant restaurant, Rider rider, Delivery delivery) {
        final Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .rider(rider)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .build();

        return order;
    }
}
