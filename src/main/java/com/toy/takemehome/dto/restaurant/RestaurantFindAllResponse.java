package com.toy.takemehome.dto.restaurant;

import com.toy.takemehome.entity.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RestaurantFindAllResponse {

    private List<RestaurantFindResponse> restaurantFindAllResponse;

    public RestaurantFindAllResponse(List<Restaurant> restaurants) {
        this.restaurantFindAllResponse = createRestaurantsResponse(restaurants);
    }

    private List<RestaurantFindResponse> createRestaurantsResponse(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantFindResponse::new)
                .collect(Collectors.toList());
    }
}
