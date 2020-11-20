package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuNameCount {

    private String name;
    private int count;

    public MenuNameCount(String name, int count) {
        checkPositiveNumber(count);
        this.name = name;
        this.count = count;
    }

    private void checkPositiveNumber(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(String.format("input count %d, count must positive number", count));
        }
    }
}
