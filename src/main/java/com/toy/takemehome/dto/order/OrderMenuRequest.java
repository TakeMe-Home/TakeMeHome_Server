package com.toy.takemehome.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderMenuRequest {

    private Long menuId;
    private int count;

    public OrderMenuRequest(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }
}
