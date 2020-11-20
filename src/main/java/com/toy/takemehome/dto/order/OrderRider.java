package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.rider.Rider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRider {

    private String name;
    private String phoneNumber;

    public OrderRider(Rider rider) {
        this.name = rider.getName();
        this.phoneNumber = rider.getPhoneNumber();
    }
}
