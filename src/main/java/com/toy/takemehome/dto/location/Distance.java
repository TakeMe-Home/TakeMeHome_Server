package com.toy.takemehome.dto.location;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Distance implements Comparable<Distance> {

    private double distance;

    public Distance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Distance o) {
        return (int) (this.distance - o.distance);
    }
}
