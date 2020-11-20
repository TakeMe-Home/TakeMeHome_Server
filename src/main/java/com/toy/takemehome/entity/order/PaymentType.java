package com.toy.takemehome.entity.order;

public enum PaymentType {
    CARD("카드"),
    CASH("현금");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }
}
