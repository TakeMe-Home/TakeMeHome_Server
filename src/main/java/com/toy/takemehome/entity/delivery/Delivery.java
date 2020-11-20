package com.toy.takemehome.entity.delivery;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.toy.takemehome.entity.delivery.DeliveryStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Builder
    public Delivery(Long id, int price, double distance, String address, DeliveryStatus status) {
        checkPositiveOrZeroPrice(price);
        this.id = id;
        this.price = price;
        this.distance = distance;
        this.address = address;
        this.status = status;
    }

    public void changeAll(int price, double distance, String address, DeliveryStatus status) {
        checkPositiveOrZeroPrice(price);
        this.price = price;
        this.distance = distance;
        this.address = address;
        this.status = status;
    }

    public boolean isAssigned() {
        return this.status != NONE && this.status != REQUEST;
    }

    public boolean isNotAssigned() {
        return !isAssigned();
    }

    public void assigned() {
        checkAssigned();
        this.status = ASSIGNED;
    }

    public void cancel() {
        this.status = NONE;
    }

    public void request() {
        this.status = REQUEST;
    }

    public void complete() {
        this.status = COMPLETE;
    }

    public void pickup() {
        this.status = PICK_UP;
    }

    public boolean isNotPickup() {
        return !isPickUp();
    }

    private boolean isPickUp() {
        return this.status == PICK_UP;
    }

    private void checkAssigned() {
        if (this.status != REQUEST) {
            throw new IllegalArgumentException(
                    String.format("current assigment status: %s, can't assigned delivery!", this.status));
        }
    }

    private void checkPositiveOrZeroPrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException(String.format("input price %d, price must positive or zero number", price));
        }
    }
}
