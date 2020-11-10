package com.toy.takemehome.repository;

import com.toy.takemehome.entity.menu.Menu;
import com.toy.takemehome.entity.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurant(Restaurant restaurant);
}
