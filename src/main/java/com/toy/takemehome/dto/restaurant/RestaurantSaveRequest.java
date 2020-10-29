package com.toy.takemehome.dto.restaurant;

import com.toy.takemehome.entity.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantSaveRequest {

    private Long ownerId;
    private String name;
    private String number;
    private String address;
    private Location location;

    public RestaurantSaveRequest(Long ownerId, String name, String number, String address, Location location) {
        this.ownerId = ownerId;
        this.name = name;
        this.number = number;
        this.address = address;
        this.location = location;
    }
}
