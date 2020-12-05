package com.toy.takemehome.service;

import com.toy.takemehome.dto.customer.CustomerOrderRequest;
import com.toy.takemehome.dto.menu.MenuIdCounts;
import com.toy.takemehome.dto.order.OrderDeliveryRequest;
import com.toy.takemehome.dto.order.OrderSaveRequest;
import com.toy.takemehome.dto.order.OrderUpdateRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.delivery.Delivery;
import com.toy.takemehome.entity.delivery.DeliveryPrice;
import com.toy.takemehome.entity.delivery.DeliveryStatus;
import com.toy.takemehome.entity.menu.Menu;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.order.OrderStatus;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;
import com.toy.takemehome.repository.*;
import com.toy.takemehome.repository.order.OrderMenuRepository;
import com.toy.takemehome.repository.order.OrderRepository;
import com.toy.takemehome.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final RiderRepository riderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public Long reception(OrderSaveRequest saveRequest) {
        final Customer customer = findCustomerById(saveRequest.getCustomerId());
        final Restaurant restaurant = findRestaurantById(saveRequest.getRestaurantId());

        double distance = restaurant.calculateDistance(customer.getLocation());
        final Delivery delivery = Delivery.builder()
                .address(customer.getAddress())
                .distance(distance)
                .price(DeliveryPrice.findPrice(distance))
                .status(DeliveryStatus.NONE)
                .build();

        final Order order = Order.createOrder(customer, restaurant, delivery, OrderStatus.ORDER, saveRequest.getPaymentType(),
                saveRequest.getPaymentStatus(), saveRequest.getTotalPrice(), saveRequest.getRequiredTime());
        orderRepository.save(order);
        saveOrderMenusRepository(order, saveRequest.getMenuIdCounts());
        return order.getId();
    }

    @Transactional
    public Long registerRequestOrder(CustomerOrderRequest customerOrderRequest) {
        final Customer customer = findCustomerById(customerOrderRequest.getCustomerId());
        final Restaurant restaurant = findRestaurantById(customerOrderRequest.getRestaurantId());

        final double distance = restaurant.calculateDistance(customer.getLocation());
        final Delivery delivery = Delivery.builder()
                .address(customer.getAddress())
                .distance(distance)
                .price(DeliveryPrice.findPrice(distance))
                .status(DeliveryStatus.NONE)
                .build();

        final Order order = Order.createOrder(customer, restaurant, delivery, OrderStatus.REQUEST, customerOrderRequest.getPaymentType(),
                customerOrderRequest.getPaymentStatus(), customerOrderRequest.getTotalPrice(), 0);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public Order findOne(Long id) {
        final Order order = findOrderByIdWithoutRider(id);
        if (order.isAssigned()) {
            findRiderById(order.getRider().getId());
        }
        return order;
    }

    public List<OrderMenu> findOrderMenus(Order order) {
        final List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrder(order);
        return orderMenus;
    }

    @Transactional
    public void update(Long id, OrderUpdateRequest updateRequest) {
        final Order order = findOrderByIdWithAll(id);
        order.update(updateRequest);
    }

    @Transactional
    public void delete(Long id) {
        final Order order = findOrderById(id);
        final List<OrderMenu> orderMenus = findOrderMenus(order);
        deleteOrderMenus(orderMenus);
        orderRepository.delete(order);
    }

    @Transactional
    public void assigned(Long orderId, Long riderId) {
        final Order order = findOrderById(orderId);
        final Rider rider = findRiderById(riderId);
        order.assigned(rider);
    }

    @Transactional
    public void cancel(Long id) {
        final Order order = findOrderById(id);
        order.cancel();
    }

    public List<Order> findAllByRequestStatus() {
        final List<Order> orders = findAllOrderByRequestStatus();
        return orders;
    }

    @Transactional
    public void requestDelivery(Long orderId, OrderDeliveryRequest orderDeliveryRequest) {
        final Order order = findOrderById(orderId);
        order.requestDelivery(orderDeliveryRequest.getCookingTime());
    }

    public List<Order> findAllByRestaurant(Long restaurantId) {
        final Restaurant restaurant = findRestaurantById(restaurantId);
        final List<Order> orders = findAllOrderByRestaurant(restaurant);
        return orders;
    }

    public List<Order> findAlLByDate(LocalDateTime startDate, LocalDateTime endDate) {
        final List<Order> orders = findAllOrderByDate(startDate, endDate);
        return orders;
    }

    public List<Order> findAllByRider(Long riderid) {
        final Rider rider = findRiderById(riderid);
        final List<Order> orders = findAllOrderByRider(rider);

        return orders;
    }

    public List<Order> findAllByRiderAssigned(Long riderId) {
        final Rider rider = findRiderById(riderId);
        final List<Order> orders = findAllOrderByRiderAssigned(rider);

        return orders;
    }

    @Transactional
    public void pickup(Long orderId) {
        final Order order = findOrderById(orderId);
        order.pickup();
    }

    @Transactional
    public void complete(Long orderId) {
        final Order order = findOrderById(orderId);
        order.complete();
    }

    private void saveOrderMenusRepository(Order order, MenuIdCounts menuIdCounts) {
        final List<OrderMenu> orderMenus = menuIdCounts.getMenuIdCounts().stream()
                .map(orderMenu -> OrderMenu.builder()
                        .menu(findMenuById(orderMenu.getMenuId()))
                        .order(order)
                        .count(orderMenu.getCount())
                        .build())
                .collect(Collectors.toList());

        orderMenus.forEach(orderMenu -> {
            if (orderMenu.isSoldOut())
                throw new IllegalArgumentException(String.format("input menu %s, sold out menu!", orderMenu.getMenu().getName()));
            orderMenuRepository.save(orderMenu);
        });
    }

    private Order findOrderByIdWithAll(Long id) {
        return orderRepository.findOneByIdWithAll(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input order id: %d, no such elementException", id)));
    }

    private Order findOrderByIdWithoutRider(Long id) {
        return orderRepository.findOneByIdWithoutRider(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input order id: %d, no such elementException", id)));
    }

    private List<Order> findAllOrderByRequestStatus() {
        return orderRepository.findAllByRequestStatus();
    }

    private void deleteOrderMenus(List<OrderMenu> orderMenus) {
        orderMenus.forEach(orderMenuRepository::delete);
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input customer id: %d, no such elementException", customerId)));
    }

    private Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findOneByIdWithOwner(restaurantId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input restaurant id: %d, no such elementException", restaurantId)));
    }

    private Rider findRiderById(Long riderId) {
        return riderRepository.findById(riderId)
                .orElseThrow(() -> new NoSuchElementException(String.format("input rider id: %d, no such elementException", riderId)));
    }

    private Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input menu id: %d, no such elementException", menuId)));
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input orderId id: %d, no such elementException", id)));
    }

    private List<Order> findAllOrderByRestaurant(Restaurant restaurant) {
        return orderRepository.findAllByRestaurant(restaurant);

    }

    private List<Order> findAllOrderByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findAllByDate(startDate, endDate);
    }

    private List<Order> findAllOrderByRider(Rider rider) {
        return orderRepository.findAllByRiderWithAll(rider);
    }

    private List<Order> findAllOrderByRiderAssigned(Rider rider) {
        return orderRepository.findAllByRiderAssigned(rider);
    }
}
