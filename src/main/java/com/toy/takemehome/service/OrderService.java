package com.toy.takemehome.service;

import com.toy.takemehome.dto.delivery.DeliveryOrderRequest;
import com.toy.takemehome.dto.menu.MenuIdCounts;
import com.toy.takemehome.dto.order.OrderSaveRequest;
import com.toy.takemehome.dto.order.OrderUpdateRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.delivery.Delivery;
import com.toy.takemehome.entity.delivery.DeliveryStatus;
import com.toy.takemehome.entity.menu.Menu;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;
import com.toy.takemehome.repository.*;
import com.toy.takemehome.repository.order.OrderMenuRepository;
import com.toy.takemehome.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

        final DeliveryOrderRequest deliveryOrderRequest = saveRequest.getDeliveryOrderRequest();
        final Delivery delivery = Delivery.builder()
                .address(customer.getAddress())
                .distance(deliveryOrderRequest.getDistance())
                .price(deliveryOrderRequest.getPrice())
                .status(DeliveryStatus.NONE)
                .build();

        final Order order = Order.createOrder(customer, restaurant, delivery);
        orderRepository.save(order);
        saveOrderMenusRepository(order, saveRequest.getMenuIdCounts());
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

    private void saveOrderMenusRepository(Order order, MenuIdCounts menuIdCounts) {
        menuIdCounts.getMenuIdCounts().stream()
                .map(orderMenu -> OrderMenu.builder()
                        .menu(findMenuById(orderMenu.getMenuId()))
                        .order(order)
                        .count(orderMenu.getCount())
                        .build())
                .forEach(orderMenuRepository::save);
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
}