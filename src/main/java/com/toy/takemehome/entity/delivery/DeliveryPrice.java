package com.toy.takemehome.entity.delivery;

import java.util.Arrays;

public enum DeliveryPrice {
    VERY_HIGH(5500, 4.0, 4.9),
    HIGH(4500, 3.0, 3.9),
    MIDDLE(3500, 2.0, 2.9),
    LOW(2500, 1.0, 1.9),
    VERY_LOW(1500, 0.1, 0.9);

    DeliveryPrice(int price, double minDistance, double maxDistance) {
        this.price = price;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    private final int price;
    private final double minDistance;
    private final double maxDistance;

    public static int findPrice(double distance) {
        return Arrays.stream(DeliveryPrice.values())
                .filter(deliveryPrice -> deliveryPrice.isRange(distance))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("distance: %f, out of range", distance)))
                .price;
    }

    private boolean isRange(double distance) {
        return greaterEqual(distance) && lesserEqual(distance);
    }

    private boolean greaterEqual(double distance) {
        return distance >= this.minDistance;
    }

    private boolean lesserEqual(double distance) {
        return distance <= this.maxDistance;
    }
}
