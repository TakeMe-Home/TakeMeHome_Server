package com.toy.takemehome.repository.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.takemehome.dto.customer.CustomerOrderListResponse;
import com.toy.takemehome.dto.location.Distance;
import com.toy.takemehome.dto.order.OrderFindAllResponse;
import com.toy.takemehome.dto.order.OrderFindResponse;
import com.toy.takemehome.dto.order.OrderNearbyResponse;
import com.toy.takemehome.entity.Location;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.customer.QCustomer;
import com.toy.takemehome.entity.delivery.DeliveryStatus;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.order.OrderStatus;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;
import com.toy.takemehome.utils.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public OrderFindAllResponse findAllByRestaurantWithMenus(Restaurant restaurant) {
        final List<Order> orders = findAllByRestaurant(restaurant);
        final List<OrderMenu> orderMenus = findOrderMenus(toOrderIds(orders));

        final Map<Long, List<OrderMenu>> orderMenusMap = createOrderMenuMap(orderMenus);

        final List<OrderFindResponse> orderFindResponses = orders.stream()
                .map(o -> new OrderFindResponse(o, orderMenusMap.get(o.getId())))
                .collect(Collectors.toList());
        return new OrderFindAllResponse(orderFindResponses);
    }

    @Override
    @Transactional
    public OrderFindAllResponse findAllRequestStatusByRestaurantWithMenus(Restaurant restaurant) {
        final List<Order> orders = findAllRequestStatusByRestaurant(restaurant);
        final List<OrderMenu> orderMenus = findOrderMenus(toOrderIds(orders));

        final Map<Long, List<OrderMenu>> orderMenusMap = createOrderMenuMap(orderMenus);

        final List<OrderFindResponse> orderFindResponses = orders.stream()
                .map(o -> new OrderFindResponse(o, orderMenusMap.get(o.getId())))
                .collect(Collectors.toList());
        return new OrderFindAllResponse(orderFindResponses);
    }

    private List<Order> findAllRequestStatusByRestaurant(Restaurant restaurant) {
        return queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .where(order.status.eq(OrderStatus.REQUEST),
                        restaurantEq(restaurant))
                .fetch();
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

        final List<Order> assignedOrders = findAssignedOrders(orders);
        innerJoinRidersForOrders(assignedOrders);

        return orders;
    }

    @Override
    public List<OrderNearbyResponse> findAllNearBy(double x, double y) {
        final List<Order> orders = findAllByRequestStatus();
        final Location targetLocation = new Location(x, y);

        final Map<Order, Distance> orderDistanceMap = createOrderDistanceMap(orders, targetLocation);
        final Map<Order, Distance> sortByValue = MapUtil.sortByValue(orderDistanceMap);

        final List<OrderNearbyResponse> orderNearbyResponses = sortByValue.keySet().stream()
                .map(o -> new OrderNearbyResponse(o, sortByValue.get(o).getDistance()))
                .collect(Collectors.toList());

        return orderNearbyResponses.size() > 10 ? orderNearbyResponses.subList(0, 11) : orderNearbyResponses;
    }

    @Override
    @Transactional
    public List<CustomerOrderListResponse> findAllCustomerOrderListByCustomer(Customer customer) {
        final List<Order> orders = findAllByCustomer(customer);
        final List<OrderMenu> orderMenus = findOrderMenus(toOrderIds(orders));

        final Map<Long, List<OrderMenu>> orderMenuMap = createOrderMenuMap(orderMenus);

        return orders.stream()
                .map(o -> new CustomerOrderListResponse(o, orderMenuMap.get(o.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllByRiderWithAll(Rider rider) {
        return queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .innerJoin(order.rider).fetchJoin()
                .fetch();
    }

    @Override
    public List<Order> findAllByRiderAssigned(Rider rider) {
        return queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .innerJoin(order.rider).fetchJoin()
                .where(assigned())
                .fetch();
    }

    @Override
    public List<Order> findAllWithAll() {
        final List<Order> orders = queryFactory
                .selectFrom(order)
                .innerJoin(order.customer).fetchJoin()
                .innerJoin(order.restaurant).fetchJoin()
                .innerJoin(order.delivery).fetchJoin()
                .fetch();

        final List<Order> assignedOrders = findAssignedOrders(orders);
        innerJoinRidersForOrders(assignedOrders);

        return orders;
    }

    private List<Long> toOrderIds(List<Order> orders) {
        return orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());
    }

    private List<Order> findAllByCustomer(Customer customer) {
        return queryFactory
                .selectFrom(order)
                .where(customerEq(customer))
                .fetch();
    }

    private BooleanExpression customerEq(Customer customer) {
        return QCustomer.customer.eq(customer);
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

    private Map<Order, Distance> createOrderDistanceMap(List<Order> orders, Location location) {
        return orders.stream()
                .collect(Collectors.toMap(o -> o, o -> new Distance(o.getTotalDistance(location))));
    }

    private List<Order> findAssignedOrders(List<Order> orders) {
        return orders.stream()
                .filter(Order::isAssigned)
                .collect(Collectors.toList());
    }

    private void innerJoinRidersForOrders(List<Order> orders) {
        queryFactory
                .selectFrom(order)
                .innerJoin(order.rider).fetchJoin()
                .where(order.id.in(toOrderIds(orders)))
                .fetch();
    }

    private BooleanExpression orderIdEq(Long orderId) {
        return order.id.eq(orderId);
    }

    private BooleanExpression orderStatusRequest() {
        return order.delivery.status.eq(DeliveryStatus.REQUEST);
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

    private BooleanExpression assigned() {
        return order.delivery.status.eq(DeliveryStatus.ASSIGNED);
    }
}
