package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuNameCount {

    private String name;
    private int count;

    public MenuNameCount(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
