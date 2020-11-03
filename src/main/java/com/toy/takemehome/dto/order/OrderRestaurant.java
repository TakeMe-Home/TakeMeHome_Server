package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRestaurant {

    private String name;
    private String number;
    private String address;

    public OrderRestaurant(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.number = restaurant.getNumber();
        this.address = restaurant.getAddress();
    }
}
