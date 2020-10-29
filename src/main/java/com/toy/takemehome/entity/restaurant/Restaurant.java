package com.toy.takemehome.entity.restaurant;

import com.toy.takemehome.entity.BaseTimeEntity;
import com.toy.takemehome.entity.Location;
import com.toy.takemehome.entity.owner.Owner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String address;

    @Embedded
    private Location location;

    @Builder
    public Restaurant(Long id, Owner owner, String name, String number, String address, Location location) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.number = number;
        this.address = address;
        this.location = location;
    }

    public void update(Owner owner, String name, String number, String address, Location location) {
        this.owner = owner;
        this.name = name;
        this.number = number;
        this.address = address;
        this.location = location;
    }
}
