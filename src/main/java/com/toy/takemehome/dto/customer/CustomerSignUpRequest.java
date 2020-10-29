package com.toy.takemehome.dto.customer;

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

    public CustomerSignUpRequest(String name, String email, String password, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
