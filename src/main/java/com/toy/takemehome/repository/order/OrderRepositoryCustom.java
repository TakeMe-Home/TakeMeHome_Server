package com.toy.takemehome.repository.order;

import com.toy.takemehome.dto.order.OrderFindAllResponse;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryCustom{
    Optional<Order> findOneByIdWithAll(Long orderId);
    Optional<Order> findOneByIdWithoutRider(Long orderId);
    List<Order> findAllByRequestStatus();
    List<Order> findAllByRestaurant(Restaurant restaurant);
    List<Order> findAllByRestaurant(Long restaurantId);
    List<OrderMenu> findOrderMenusByOrderId(Long orderId);
    OrderFindAllResponse findAllByRestaurantWithMenus(Long RestaurantId);
}
