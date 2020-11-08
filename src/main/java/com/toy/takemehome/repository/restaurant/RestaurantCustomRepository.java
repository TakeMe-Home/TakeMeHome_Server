package com.toy.takemehome.repository.restaurant;

import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.entity.restaurant.Restaurant;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantCustomRepository {
    List<Restaurant> findAllByOwner(@Param("owner")Owner owner);
}
