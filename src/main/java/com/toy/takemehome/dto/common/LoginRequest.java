package com.toy.takemehome.dto.common;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @Email
    private String email;
    private String password;

    @NotNull
    private String token;

    public LoginRequest(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }
}
