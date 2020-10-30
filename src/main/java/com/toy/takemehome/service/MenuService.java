package com.toy.takemehome.service;

import com.toy.takemehome.dto.menu.MenuRegisterRequest;
import com.toy.takemehome.entity.menu.Menu;
import com.toy.takemehome.entity.menu.MenuStatus;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.repository.MenuRepository;
import com.toy.takemehome.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Long register(MenuRegisterRequest registerRequest) {
        final Restaurant restaurant = findRestaurantById(registerRequest.getRestaurantId());

        final Menu menu = Menu.builder()
                .name(registerRequest.getName())
                .price(registerRequest.getPrice())
                .restaurant(restaurant)
                .status(MenuStatus.SALE)
                .build();

        menuRepository.save(menu);
        return menu.getId();
    }

    private Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findOneByIdWithOwner(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input restaurant id: %d, no such elementException", id)));
    }
}
