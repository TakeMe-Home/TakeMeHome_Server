package com.toy.takemehome.dto.order;

import com.toy.takemehome.entity.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCustomer {

    private Long id;
    private String name;
    private String phoneNumber;

    public OrderCustomer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
    }
}
