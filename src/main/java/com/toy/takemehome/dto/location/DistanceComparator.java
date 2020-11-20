package com.toy.takemehome.dto.location;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Distance> {

    @Override
    public int compare(Distance standardDistance, Distance targetDistance) {
        return standardDistance.compareTo(targetDistance);
    }
}
