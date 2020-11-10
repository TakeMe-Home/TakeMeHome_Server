package com.toy.takemehome.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static java.lang.Math.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    private double x;
    private double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double calculateDistance(Location location) {
        double theta = this.y - location.y;
        double dist = (sin(convertDegToRad(this.x)) * sin(convertDegToRad(location.x)))
                + (cos(convertDegToRad(this.x)) * cos(convertDegToRad(location.x))
                * cos(convertDegToRad(theta)));

        dist = acos(dist);
        dist = convertRadToDeg(dist);
        dist *= (60 * 1.1515);

        final double kilometer = convertKilometer(dist);
        return convertDecimalPointFirst(kilometer);
    }

    private double convertDegToRad(double deg) {
        return (deg * PI / 180.0);
    }

    private double convertRadToDeg(double rad) {
        return (rad * 180 / PI);
    }

    private double convertKilometer(double dist) {
        return dist * 1.609344;
    }

    private double convertDecimalPointFirst(double kilometer) {
        return ceil((kilometer * 10)) / 10.0;
    }
}
