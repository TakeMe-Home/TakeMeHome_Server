package com.toy.takemehome.dto.customer;

import com.toy.takemehome.entity.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class CustomerUpdateRequest {

    private String name;

    @Email
    private String email;

    private String password;
    private String phoneNumber;
    private String address;
    private Location location;

    public CustomerUpdateRequest(String name, @Email String email, String password,
                                 String phoneNumber, String address, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.location = location;
    }
}
