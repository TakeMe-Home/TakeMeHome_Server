package com.toy.takemehome.dto.restaurant;

import com.toy.takemehome.dto.owner.OwnerDetail;
import com.toy.takemehome.entity.Location;
import com.toy.takemehome.entity.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantDetail {

    private Long id;
    private OwnerDetail owner;
    private String name;
    private String number;
    private String address;
    private Location location;

    public RestaurantDetail(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.owner = new OwnerDetail(restaurant.getOwner());
        this.name = restaurant.getName();
        this.number = restaurant.getNumber();
        this.address = restaurant.getAddress();
        this.location = restaurant.getLocation();
    }
}
