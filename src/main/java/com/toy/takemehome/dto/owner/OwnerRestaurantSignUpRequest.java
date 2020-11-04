package com.toy.takemehome.dto.owner;

import com.toy.takemehome.dto.restaurant.RestaurantSaveRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerRestaurantSignUpRequest {

    OwnerSignUpRequest ownerSignUpRequest;
    RestaurantSaveRequest restaurantSaveRequest;

    public OwnerRestaurantSignUpRequest(OwnerSignUpRequest ownerSignUpRequest, RestaurantSaveRequest restaurantSaveRequest) {
        this.ownerSignUpRequest = ownerSignUpRequest;
        this.restaurantSaveRequest = restaurantSaveRequest;
    }
}
