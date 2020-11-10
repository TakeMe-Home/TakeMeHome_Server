package com.toy.takemehome.dto.location;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationDetail {

    private double x;
    private double y;

    public LocationDetail(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
