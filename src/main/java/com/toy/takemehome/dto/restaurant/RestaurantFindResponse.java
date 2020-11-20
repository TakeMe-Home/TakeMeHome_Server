package com.toy.takemehome.dto.restaurant;

import com.toy.takemehome.entity.Location;
import com.toy.takemehome.entity.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantFindResponse {

    private Long id;
    private String name;
    private String number;
    private String address;
    private Location location;

    public RestaurantFindResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.number = restaurant.getNumber();
        this.address = restaurant.getAddress();
        this.location = restaurant.getLocation();
    }
}
