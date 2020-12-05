package com.toy.takemehome.api;

import com.toy.takemehome.dto.customer.CustomerOrderListResponse;
import com.toy.takemehome.dto.order.*;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.entity.rider.Rider;
import com.toy.takemehome.repository.order.OrderMenuRepository;
import com.toy.takemehome.repository.order.OrderRepository;
import com.toy.takemehome.repository.restaurant.RestaurantRepository;
import com.toy.takemehome.service.CustomerService;
import com.toy.takemehome.service.OrderService;
import com.toy.takemehome.service.RestaurantService;
import com.toy.takemehome.service.RiderService;
import com.toy.takemehome.service.fcm.FirebaseCloudMessageService;
import com.toy.takemehome.utils.DefaultRes;
import com.toy.takemehome.utils.notification.NotificationBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;
import static com.toy.takemehome.utils.notification.NotificationTitle.*;
import static java.util.stream.Collectors.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private final CustomerService customerService;
    private final RiderService riderService;
    private final RestaurantService restaurantService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/reception")
    public DefaultRes<Long> reception(@RequestBody OrderSaveRequest saveRequest) {
        try {
            final Long id = orderService.reception(saveRequest);
            final Customer customer = customerService.findOneById(saveRequest.getCustomerId());
            firebaseCloudMessageService.sendMessageTo(
                    Arrays.asList(customer.getToken()), ORDER_RECEPTION, NotificationBody.orderReceptionWithTime(saveRequest.getRequiredTime()));
            return DefaultRes.res(OK, CREATE_ORDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_ORDER_FAIL);
        }
    }

    @PostMapping("/refuse")
    public DefaultRes refuse(@RequestBody OrderRefuseRequest refuseRequest) {
        try {
            final Customer customer = customerService.findOneById(refuseRequest.getCustomerId());
            firebaseCloudMessageService.sendMessageTo(
                    Arrays.asList(customer.getToken()), ORDER_REFUSE, NotificationBody.orderRefuseWithTime(refuseRequest.getReason()));
            return DefaultRes.res(OK, CANCEL_ORDER);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CANCEL_ORDER_FAIL);
        }
    }

    @GetMapping("/order/{id}")
    public DefaultRes<OrderFindResponse> findOne(@PathVariable("id") Long id) {
        try {
            final Order order = orderService.findOne(id);
            final List<OrderMenu> orderMenus = orderService.findOrderMenus(order);

            OrderFindResponse orderFindResponse = new OrderFindResponse(order, orderMenus);
            return DefaultRes.res(OK, FIND_ORDER, orderFindResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_ORDER);
        }
    }

    @PutMapping("/order/{id}")
    public DefaultRes<Long> update(@PathVariable("id") Long id,
                                   @RequestBody OrderUpdateRequest updateRequest) {
        try {
            orderService.update(id, updateRequest);
            return DefaultRes.res(OK, UPDATE_ORDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, UPDATE_ORDER_FAIL);
        }
    }

    @DeleteMapping("/order/{id}")
    public DefaultRes<Long> delete(@PathVariable("id") Long id) {
        try {
            orderService.delete(id);
            return DefaultRes.res(OK, CANCEL_ORDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CANCEL_ORDER_FAIL);
        }
    }

    @PutMapping("/order/{orderId}/assigned/{riderId}")
    public DefaultRes<Long> assigned(@PathVariable("orderId") Long orderId,
                                     @PathVariable("riderId") Long riderId) {
        try {
            orderService.assigned(orderId, riderId);
            return DefaultRes.res(OK, ASSIGNED_ORDER, orderId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, ASSIGNED_ORDER_FAIL);
        }
    }

    @PutMapping("/order/{id}/cancel")
    public DefaultRes<Long> cancel(@PathVariable("id") Long id) {
        try {
            orderService.cancel(id);
            return DefaultRes.res(OK, CANCEL_ORDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CANCEL_ORDER_FAIL);
        }
    }

    @GetMapping("status/request")
    public DefaultRes<OrderFindAllRequestStatusResponse> findAllRequestStatusDto() {
        try {
            final List<Order> orders = orderService.findAllByRequestStatus();
            final OrderFindAllRequestStatusResponse orderFindAllRequestStatusResponse = new OrderFindAllRequestStatusResponse(orders);

            return DefaultRes.res(OK, FIND_ORDER, orderFindAllRequestStatusResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, NOT_FOUND_ORDER);
        }
    }

    @PutMapping("/order/{orderId}/request/delivery/")
    public DefaultRes<Long> requestDelivery(@PathVariable("orderId") Long orderId,
                                            @RequestBody OrderDeliveryRequest orderDeliveryRequest) {
        try {
            orderService.requestDelivery(orderId, orderDeliveryRequest);
            firebaseCloudMessageService.sendMessageTo(riderService.findAll()
                    .stream()
                    .map(Rider::getToken)
                    .collect(toList()), DELIVERY_REQUEST, NotificationBody.DELIVERY_REQUEST);
            return DefaultRes.res(OK, ORDER_DELIVERY_REQUEST, orderId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, ORDER_DELIVERY_REQUEST_FAIL);
        }
    }

    @GetMapping("/{restaurantId}")
    public DefaultRes<OrderFindAllResponse> findAllByRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        try {
            final Restaurant restaurant = restaurantService.findOneById(restaurantId);
            final OrderFindAllResponse orderFindAllResponse = orderRepository.findAllByRestaurantWithMenus(restaurant);
            return DefaultRes.res(OK, FIND_ORDER, orderFindAllResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, NOT_FOUND_ORDER);
        }
    }

    @GetMapping("/between")
    public DefaultRes<OrdersResponseWithoutMenu> findAllByDate(@RequestParam LocalDateTime startDate,
                                                               @RequestParam LocalDateTime endDate) {
        try {
            final List<Order> orders = orderService.findAlLByDate(startDate, endDate);
            final OrdersResponseWithoutMenu ordersResponse = new OrdersResponseWithoutMenu(orders);
            return DefaultRes.res(OK, FIND_ORDER, ordersResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_ORDER);
        }
    }

    @GetMapping("nearby")
    public DefaultRes<List<OrderNearbyResponse>> findAllNearby(@RequestParam double x,
                                                               @RequestParam double y) {
        try {
            final List<OrderNearbyResponse> orderNearbyResponses = orderRepository.findAllNearBy(x, y);
            return DefaultRes.res(OK, FIND_ORDER, orderNearbyResponses);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_ORDER);
        }
    }

    @GetMapping("/riders/{riderId}")
    public DefaultRes<List<OrderResponseWithoutMenu>> findAllByRider(@PathVariable("riderId") Long riderId) {
        try {
            final List<Order> orders = orderService.findAllByRider(riderId);
            final List<OrderResponseWithoutMenu> ordersResponseWithoutMenu = orders.stream()
                    .map(OrderResponseWithoutMenu::new)
                    .collect(toList());

            return DefaultRes.res(OK, FIND_ORDER, ordersResponseWithoutMenu);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_ORDER);
        }
    }

    @GetMapping("/riders/{riderId}/assigned")
    public DefaultRes<List<OrderResponseWithoutMenu>> findAllByRiderAssigned(@PathVariable("riderId") Long riderId) {
        try {
            final List<Order> orders = orderService.findAllByRiderAssigned(riderId);
            final List<OrderResponseWithoutMenu> ordersResponseWithoutMenu = orders.stream()
                    .map(OrderResponseWithoutMenu::new)
                    .collect(toList());

            return DefaultRes.res(OK, FIND_ORDER, ordersResponseWithoutMenu);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_ORDER);
        }
    }

    @GetMapping("/customers/{customerId}")
    public DefaultRes<List<CustomerOrderListResponse>> findAllByCustomer(@PathVariable("customerId") Long customerId) {
        try {
            final Customer customer = customerService.findOneById(customerId);
            List<CustomerOrderListResponse> customerOrderListResponses = orderRepository.findAllCustomerOrderListByCustomer(customer);

            return DefaultRes.res(OK, FIND_ORDER, customerOrderListResponses);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_ORDER);
        }
    }

    @PutMapping("/order/{orderId}/pickup")
    public DefaultRes<Long> pickup(@PathVariable("orderId") Long orderId) {
        try {
            orderService.pickup(orderId);
            return DefaultRes.res(OK, ORDER_PICKUP, orderId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, ORDER_PICKUP_FAIL);
        }
    }

    @PutMapping("/order/{orderId}/complete")
    public DefaultRes<Long> complete(@PathVariable("orderId") Long orderId) {
        try {
            orderService.complete(orderId);
            return DefaultRes.res(OK, ORDER_COMPLETE, orderId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, ORDER_COMPLETE_FAIL);
        }
    }
}
