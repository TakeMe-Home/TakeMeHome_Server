package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuRegisterRequest {

    private Long restaurantId;
    private String name;
    private int price;

    public MenuRegisterRequest(Long restaurantId, String name, int price) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
    }
}
