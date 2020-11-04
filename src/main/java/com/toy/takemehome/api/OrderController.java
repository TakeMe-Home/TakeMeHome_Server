package com.toy.takemehome.api;

import com.toy.takemehome.dto.order.OrderFindResponse;
import com.toy.takemehome.dto.order.OrderSaveRequest;
import com.toy.takemehome.dto.order.OrderUpdateRequest;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import com.toy.takemehome.service.OrderService;
import com.toy.takemehome.utils.DefaultRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/reception")
    public DefaultRes<Long> reception(@RequestBody OrderSaveRequest saveRequest) {
        try {
            final Long id = orderService.reception(saveRequest);
            return DefaultRes.res(OK, CREATE_ORDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_ORDER_FAIL);
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
}