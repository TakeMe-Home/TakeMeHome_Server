package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuIdCount {

    private Long menuId;
    private int count;

    public MenuIdCount(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }
}
