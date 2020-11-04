package com.toy.takemehome.repository.order;

import com.toy.takemehome.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryCustom{
    Optional<Order> findOneByIdWithAll(Long orderId);
    Optional<Order> findOneByIdWithoutRider(Long orderId);
    List<Order> findAllByRequestStatus();
}
