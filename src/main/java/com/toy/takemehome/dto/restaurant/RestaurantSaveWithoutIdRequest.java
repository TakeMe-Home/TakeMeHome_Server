package com.toy.takemehome.dto.restaurant;

import com.toy.takemehome.entity.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantSaveWithoutIdRequest {

    private String name;
    private String number;
    private String address;
    private Location location;

    public RestaurantSaveWithoutIdRequest(String name, String number, String address, Location location) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.location = location;
    }
}
