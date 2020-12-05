package com.toy.takemehome.dto.customer;

import com.toy.takemehome.dto.menu.MenuNameCount;
import com.toy.takemehome.dto.menu.MenuNameCounts;
import com.toy.takemehome.entity.order.Order;
import com.toy.takemehome.entity.order.OrderMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CustomerOrderListResponse {

    private LocalDateTime orderTime;
    private MenuNameCounts menuNameCounts;
    private int totalPrice;
    private String orderStatus;

    public CustomerOrderListResponse(Order order, List<OrderMenu> orderMenus) {
        this.orderTime = order.getCreatedDate();
        this.menuNameCounts = new MenuNameCounts(createMenuNameCounts(orderMenus));
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getStatus().name();
    }

    private List<MenuNameCount> createMenuNameCounts(List<OrderMenu> orderMenus) {
        return orderMenus.stream()
                .map(orderMenu -> new MenuNameCount(orderMenu.getMenu().getName(), orderMenu.getCount()))
                .collect(Collectors.toList());
    }
}
