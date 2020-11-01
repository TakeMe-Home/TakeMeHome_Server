package com.toy.takemehome.service;

import com.toy.takemehome.dto.delivery.DeliveryOrderRequest;
import com.toy.takemehome.dto.order.OrderMenusRequest;
import com.toy.takemehome.dto.order.OrderSaveRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.delivery.Delivery;
import com.toy.takemehome.entity.delivery.DeliveryStatus;
import com.toy.takemehome.entity.menu.Menu;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;
import com.toy.takemehome.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long save(OrderSaveRequest saveRequest) {
        final Customer customer = findCustomerById(saveRequest.getCustomerId());
        final Restaurant restaurant = findRestaurantById(saveRequest.getRestaurantId());
        final Rider rider = findRiderById(saveRequest.getRiderId());

        final DeliveryOrderRequest deliveryOrderRequest = saveRequest.getDeliveryOrderRequest();
        final Delivery delivery = Delivery.builder()
                .address(customer.getAddress())
                .distance(deliveryOrderRequest.getDistance())
                .price(deliveryOrderRequest.getPrice())
                .status(DeliveryStatus.NONE)
                .build();

        final Order order = Order.createOrder(customer, restaurant, rider, delivery);
        saveOrderMenusRepository(order, saveRequest.getOrderMenusRequest());

        orderRepository.save(order);
        return order.getId();
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

    private void saveOrderMenusRepository(Order order, OrderMenusRequest orderMenusRequest) {
        orderMenusRequest.getOrderMenusRequest().stream()
                .map(orderMenu -> OrderMenu.builder()
                        .menu(findMenuById(orderMenu.getMenuId()))
                        .order(order)
                        .count(orderMenu.getCount())
                        .build())
                .forEach(orderMenuRepository::save);
    }

    private Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input menu id: %d, no such elementException", menuId)));
    }
}
