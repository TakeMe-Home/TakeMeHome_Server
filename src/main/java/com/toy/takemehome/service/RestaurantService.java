package com.toy.takemehome.service;

import com.toy.takemehome.dto.restaurant.RestaurantSaveRequest;
import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.repository.OwnerRepository;
import com.toy.takemehome.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

    private Owner findOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input owner id: %d, no such elementException", ownerId)));

    }
}
