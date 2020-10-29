package com.toy.takemehome.entity.delivery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private int distance;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
