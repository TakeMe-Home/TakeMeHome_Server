package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuIdCount {

    private Long menuId;
    private int count;

    public MenuIdCount(Long menuId, int count) {
        checkPositiveNumber(count);

        this.menuId = menuId;
        this.count = count;
    }

    private void checkPositiveNumber(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(String.format("input count %d, count must positive number", count));
        }
    }
}
