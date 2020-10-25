package com.toy.takemehome.dto.customer;

import com.toy.takemehome.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerUpdateRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Address address;

    public CustomerUpdateRequest(String name, String email, String password, String phoneNumber, Address address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
