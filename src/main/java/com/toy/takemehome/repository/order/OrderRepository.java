package com.toy.takemehome.repository.order;

import com.toy.takemehome.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    @Query("select o from Order o " +
            "join fetch o.customer " +
            "join fetch o.restaurant " +
            "join fetch o.delivery " +
            "where o.id = :id")
    Optional<Order> findOrderByIdWithAll(@Param("id") Long id);
}
