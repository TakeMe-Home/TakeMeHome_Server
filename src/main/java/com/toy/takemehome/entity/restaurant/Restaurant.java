package com.toy.takemehome.entity.restaurant;

import com.toy.takemehome.entity.Address;
import com.toy.takemehome.entity.BaseTimeEntity;
import com.toy.takemehome.entity.Location;
import com.toy.takemehome.entity.owner.Owner;
import lombok.AccessLevel;
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

    @Embedded
    private Address address;

    @Embedded
    private Location location;
}
