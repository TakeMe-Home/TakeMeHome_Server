package com.toy.takemehome.dto.customer;

import com.toy.takemehome.entity.Location;
import com.toy.takemehome.entity.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerDetail {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private Location location;

    public CustomerDetail(Long id, String name, String email, String password, String address, Location location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.location = location;
    }

    public CustomerDetail(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.address = customer.getAddress();
        this.location = customer.getLocation();
    }
}
