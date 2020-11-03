package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MenuIdCounts {

    private List<MenuIdCount> menuIdCounts;

    public MenuIdCounts(List<MenuIdCount> menuIdCounts) {
        this.menuIdCounts = menuIdCounts;
    }
}
