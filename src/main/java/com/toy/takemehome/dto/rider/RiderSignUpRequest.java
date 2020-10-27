package com.toy.takemehome.dto.rider;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RiderSignUpRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public RiderSignUpRequest(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
