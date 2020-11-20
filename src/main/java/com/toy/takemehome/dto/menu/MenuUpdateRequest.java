package com.toy.takemehome.dto.menu;

import com.toy.takemehome.entity.menu.MenuStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuUpdateRequest {

    private String name;
    private int price;
    private MenuStatus menuStatus;

    public MenuUpdateRequest(String name, int price, MenuStatus menuStatus) {
        this.name = name;
        this.price = price;
        this.menuStatus = menuStatus;
    }
}
