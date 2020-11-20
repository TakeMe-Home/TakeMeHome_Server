package com.toy.takemehome.dto.customer;

import com.toy.takemehome.entity.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerSignUpRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Location location;

    public CustomerSignUpRequest(String name, String email, String password, String phoneNumber, String address, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.location = location;
    }
}
