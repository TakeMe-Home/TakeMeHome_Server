package com.toy.takemehome.dto.menu;

import com.toy.takemehome.entity.menu.Menu;
import com.toy.takemehome.entity.menu.MenuStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuDetail {

    private Long id;
    private String name;
    private int price;
    private MenuStatus menuStatus;

    public MenuDetail(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.menuStatus = menu.getStatus();
    }
}
