package com.toy.takemehome.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.takemehome.entity.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.toy.takemehome.entity.order.OrderStatus.*;
import static com.toy.takemehome.entity.order.QOrder.*;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Order> findOneByIdWithAll(Long orderId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .innerJoin(order.rider).fetchJoin()
                .where(orderIdEq(orderId))
                .fetchOne());
    }

    @Override
    public Optional<Order> findOneByIdWithoutRider(Long orderId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .where(orderIdEq(orderId))
                .fetchOne());
    }

    @Override
    public List<Order> findAllByRequestStatus() {
        return queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .where(orderStatusRequest())
                .fetch();
    }

    private BooleanExpression orderStatusRequest() {
        return order.status.eq(REQUEST);
    }

    private BooleanExpression orderIdEq(Long orderId) {
        return order.id.eq(orderId);
    }
}
