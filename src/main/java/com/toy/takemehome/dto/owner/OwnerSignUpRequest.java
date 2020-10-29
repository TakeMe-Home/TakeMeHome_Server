package com.toy.takemehome.dto.owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerSignUpRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    public OwnerSignUpRequest(String name, String email, String password, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
