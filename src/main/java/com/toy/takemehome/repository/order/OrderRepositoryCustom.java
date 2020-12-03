package com.toy.takemehome.repository.order;

import com.toy.takemehome.dto.customer.CustomerOrderListResponse;
import com.toy.takemehome.dto.order.OrderFindAllResponse;
import com.toy.takemehome.dto.order.OrderNearbyResponse;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepositoryCustom{
    Optional<Order> findOneByIdWithAll(Long orderId);
    Optional<Order> findOneByIdWithoutRider(Long orderId);
    List<Order> findAllByRequestStatus();
    List<Order> findAllByRestaurant(Restaurant restaurant);
    List<Order> findAllByRestaurant(Long restaurantId);
    List<OrderMenu> findOrderMenusByOrderId(Long orderId);
    List<Order> findAllByDate(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findAllWithAll();
    List<Order> findAllByRiderWithAll(Rider rider);
    List<Order> findAllByRiderAssigned(Rider rider);

    OrderFindAllResponse findAllByRestaurantWithMenus(Restaurant restaurant);
    List<OrderNearbyResponse> findAllNearBy(double x, double y);
    List<CustomerOrderListResponse> findAllCustomerOrderListByCustomer(Customer customer);

}
