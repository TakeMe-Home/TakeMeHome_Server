package com.toy.takemehome.repository.order;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.takemehome.dto.order.OrderFindAllResponse;
import com.toy.takemehome.dto.order.OrderFindResponse;
import com.toy.takemehome.entity.menu.QMenu;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.QRider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.toy.takemehome.entity.menu.QMenu.*;
import static com.toy.takemehome.entity.order.OrderStatus.*;
import static com.toy.takemehome.entity.order.QOrder.*;
import static com.toy.takemehome.entity.order.QOrderMenu.*;

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

    @Override
    public List<Order> findAllByRestaurant(Restaurant restaurant) {
        return queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .where(restaurantEq(restaurant))
                .fetch();
    }

    @Override
    public List<Order> findAllByRestaurant(Long restaurantId) {
        return queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .where(restaurantEq(restaurantId))
                .fetch();
    }

    @Override
    public List<OrderMenu> findOrderMenusByOrderId(Long orderId) {
        return queryFactory
                .selectFrom(orderMenu)
                .innerJoin(orderMenu.menu).fetchJoin()
                .where(orderIdEq(orderId))
                .fetch();
    }

    @Override
    @Transactional
    public OrderFindAllResponse findAllByRestaurantWithMenus(Long restaurantId) {
        final List<Order> orders = findAllByRestaurant(restaurantId);
        final List<OrderMenu> orderMenus = findOrderMenus(toOrderIds(orders));

        final Map<Long, List<OrderMenu>> orderMenusMap = createOrderMenuMap(orderMenus);

        final List<OrderFindResponse> orderFindResponses = orders.stream()
                .map(o -> new OrderFindResponse(o, orderMenusMap.get(o.getId())))
                .collect(Collectors.toList());
        return new OrderFindAllResponse(orderFindResponses);
    }

    @Override
    @Transactional
    public List<Order> findAllByDate(LocalDateTime startDate, LocalDateTime endDate) {
        final List<Order> orders = queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .where(dateBetween(startDate, endDate))
                .fetch();

        final List<Order> assignedOrders = orders.stream()
                .filter(Order::isAssigned)
                .collect(Collectors.toList());

        queryFactory
                .selectFrom(order)
                .innerJoin(order.rider).fetchJoin()
                .where(order.id.in(toOrderIds(assignedOrders)))
                .fetch();

        return orders;
    }

    private List<Long> toOrderIds(List<Order> orders) {
        return orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());
    }

    private List<OrderMenu> findOrderMenus(List<Long> orderIds) {
        return queryFactory
                .selectFrom(orderMenu)
                .innerJoin(orderMenu.menu)
                .where(orderMenu.order.id.in(orderIds))
                .fetch();
    }

    private Map<Long, List<OrderMenu>> createOrderMenuMap(List<OrderMenu> orderMenus) {
        return orderMenus.stream()
                .collect(Collectors.groupingBy(om -> om.getOrder().getId()));
    }

    private BooleanExpression orderIdEq(Long orderId) {
        return order.id.eq(orderId);
    }

    private BooleanExpression orderStatusRequest() {
        return order.status.eq(REQUEST);
    }

    private BooleanExpression restaurantEq(Restaurant restaurant) {
        return order.restaurant.eq(restaurant);
    }

    private BooleanExpression restaurantEq(Long restaurantId) {
        return order.restaurant.id.eq(restaurantId);
    }

    private BooleanExpression dateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return order.createdDate.between(startDate, endDate);
    }
}
