package com.toy.takemehome.entity.menu;

import com.toy.takemehome.entity.restaurant.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.toy.takemehome.entity.menu.MenuStatus.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "retaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @Builder
    public Menu(Long id, Restaurant restaurant, String name, int price, MenuStatus status) {
        this.id = id;
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public void update(String name, int price, MenuStatus status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public void soldOut() {
        this.status = SOLD_OUT;
    }

    public void sale() {
        this.status = SALE;
    }

    public boolean isSoldOut() {
        return this.status == SOLD_OUT;
    }

    public boolean isNotSoldOut() {
        return !isSoldOut();
    }
}
