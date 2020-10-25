package com.toy.takemehome.dto.customer;

import com.toy.takemehome.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerSignUpRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Address address;

    public CustomerSignUpRequest(String name, String email, String password, Address address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
