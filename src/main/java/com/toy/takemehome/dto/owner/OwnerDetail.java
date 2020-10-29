package com.toy.takemehome.dto.owner;

import com.toy.takemehome.entity.owner.Owner;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerDetail {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    public OwnerDetail(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.password = owner.getPassword();
        this.phoneNumber = owner.getPhoneNumber();
        this.address = owner.getAddress();
    }
}
