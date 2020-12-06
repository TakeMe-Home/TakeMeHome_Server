package com.toy.takemehome.dto.restaurant;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantNameAddressDto {

    private String restaurantName;
    private String restaurantAddress;

    public RestaurantNameAddressDto(String restaurantName, String restaurantAddress) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
    }
}
