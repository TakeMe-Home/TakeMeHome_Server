package com.toy.takemehome.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRefuseRequest {

    private Long customerId;
    private String reason;

    public OrderRefuseRequest(Long customerId, String reason) {
        this.customerId = customerId;
        this.reason = reason;
    }
}
