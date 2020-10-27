package com.toy.takemehome.dto.rider;

import com.toy.takemehome.entity.rider.Rider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RiderDetail {

    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public RiderDetail(Rider rider) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
