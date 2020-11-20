package com.toy.takemehome.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class MenuNameCounts {

    @NotEmpty
    private List<MenuNameCount> menuNameCounts;

    public MenuNameCounts(List<MenuNameCount> menuNameCounts) {
        this.menuNameCounts = menuNameCounts;
    }
}
