package com.toy.takemehome.repository;

import com.toy.takemehome.entity.order.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
