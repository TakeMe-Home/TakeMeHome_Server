package com.toy.takemehome.dto.owner;

import com.toy.takemehome.dto.restaurant.RestaurantSaveWithoutIdRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerRestaurantSignUpRequest {

    OwnerSignUpRequest ownerSignUpRequest;
    RestaurantSaveWithoutIdRequest restaurantSaveWithoutIdRequest;

    public OwnerRestaurantSignUpRequest(OwnerSignUpRequest ownerSignUpRequest, RestaurantSaveWithoutIdRequest restaurantSaveWithoutIdRequest) {
        this.ownerSignUpRequest = ownerSignUpRequest;
        this.restaurantSaveWithoutIdRequest = restaurantSaveWithoutIdRequest;
    }
}
