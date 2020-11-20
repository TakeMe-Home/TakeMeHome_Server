package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class MenuIdCounts {

    @NotEmpty
    private List<MenuIdCount> menuIdCounts;

    public MenuIdCounts(List<MenuIdCount> menuIdCounts) {
        this.menuIdCounts = menuIdCounts;
    }
}
