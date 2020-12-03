package com.toy.takemehome.repository.order;

import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    @Query("select om from OrderMenu om " +
            "join fetch om.menu " +
            "where om.order = :order")
    List<OrderMenu> findAllByOrder(@Param("order") Order order);

    @Query("select om from OrderMenu om " +
            "join fetch om.menu " +
            "join fetch om.order " +
            "where om.order.customer = :customer")
    List<OrderMenu> findAllByCustomer(@Param("customer") Customer customer);
}
