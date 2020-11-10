package com.toy.takemehome.dto.rider;

import com.toy.takemehome.entity.rider.Rider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RiderDetail {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public RiderDetail(Rider rider) {
        this.id = rider.getId();
        this.name = rider.getName();
        this.email = rider.getEmail();
        this.password = rider.getPassword();
        this.phoneNumber = rider.getPhoneNumber();
    }
}
