package com.toy.takemehome.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.toy.takemehome.entity.customer.QCustomer.*;
import static com.toy.takemehome.entity.delivery.QDelivery.*;
import static com.toy.takemehome.entity.order.QOrder.*;
import static com.toy.takemehome.entity.restaurant.QRestaurant.*;
import static com.toy.takemehome.entity.rider.QRider.*;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Order> findOneByIdWithAll(Long orderId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(order)
                .leftJoin(order.customer, customer)
                .leftJoin(order.restaurant, restaurant)
                .leftJoin(order.delivery, delivery)
                .leftJoin(order.rider, rider)
                .where(order.id.eq(orderId))
                .fetchOne());
    }
}
