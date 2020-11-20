package com.toy.takemehome.entity.order;

import com.toy.takemehome.entity.menu.Menu;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private int count;

    @Builder
    public OrderMenu(Long id, Order order, Menu menu, int count) {
        checkPositiveNumber(count);

        this.id = id;
        this.order = order;
        this.menu = menu;
        this.count = count;
    }

    public boolean isSoldOut() {
        return this.getMenu().isSoldOut();
    }

    private void checkPositiveNumber(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(String.format("input count %d, must positive or zero number!", count));
        }
    }
}
