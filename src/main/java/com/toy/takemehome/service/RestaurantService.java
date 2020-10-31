package com.toy.takemehome.service;

import com.toy.takemehome.dto.restaurant.RestaurantSaveRequest;
import com.toy.takemehome.dto.restaurant.RestaurantUpdateRequest;
import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.repository.OwnerRepository;
import com.toy.takemehome.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    @Transactional
    public Long saveWithOwner(RestaurantSaveRequest saveRequest) {
        final Owner owner = findOwnerById(saveRequest.getOwnerId());

        final Restaurant restaurant = Restaurant.builder()
                .name(saveRequest.getName())
                .number(saveRequest.getNumber())
                .owner(owner)
                .address(saveRequest.getAddress())
                .location(saveRequest.getLocation())
                .build();

        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    public Restaurant findOneById(Long id) {
        final Restaurant restaurant = findRestaurantById(id);
        return restaurant;
    }

    @Transactional
    public void update(Long id, RestaurantUpdateRequest updateRequest) {
        final Restaurant restaurant = findRestaurantById(id);
        final Owner owner = findOwnerById(updateRequest.getOwnerId());

        restaurant.update(owner, restaurant.getName(), restaurant.getNumber(), restaurant.getAddress(),
                restaurant.getLocation());
    }

    @Transactional
    public void delete(Long id) {
        final Restaurant restaurant = findRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    private Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findOneByIdWithOwner(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input owner id: %d, no such elementException", id)));
    }

    private Owner findOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input owner id: %d, no such elementException", ownerId)));

    }
}
