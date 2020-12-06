package com.toy.takemehome.service;

import com.toy.takemehome.dto.restaurant.RestaurantSaveRequest;
import com.toy.takemehome.dto.restaurant.RestaurantUpdateRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.delivery.DeliveryPrice;
import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.repository.CustomerRepository;
import com.toy.takemehome.repository.OwnerRepository;
import com.toy.takemehome.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;

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

        restaurant.update(owner, updateRequest.getName(), updateRequest.getNumber(), updateRequest.getAddress(),
                updateRequest.getLocation());
    }

    @Transactional
    public void delete(Long id) {
        final Restaurant restaurant = findRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    public List<Restaurant> findAll() {
        final List<Restaurant> restaurants = findAllRestaurant();
        return restaurants;
    }

    public List<Restaurant> findAllByOwner(Long ownerId) {
        final Owner owner = findOwnerById(ownerId);
        final List<Restaurant> restaurants = findAllRestaurantByOwner(owner);
        return restaurants;
    }

    public int findDeliveryPrice(Long restaurantId, Long customerId) {
        final Restaurant restaurant = findRestaurantById(restaurantId);
        final Customer customer = findCustomerById(customerId);

        final double distance = restaurant.calculateDistance(customer.getLocation());
        return DeliveryPrice.findPrice(distance);
    }

    public Restaurant findByNameAndAddress(String name, String address) {
        return findRestaurantByNameAndAddress(name, address);
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input customer id: %d, no such elementException", customerId)));
    }

    private Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findOneByIdWithOwner(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input restaurant id: %d, no such elementException", id)));
    }

    private Owner findOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input owner id: %d, no such elementException", ownerId)));

    }

    private List<Restaurant> findAllRestaurant() {
        return restaurantRepository.findAll();
    }

    private List<Restaurant> findAllRestaurantByOwner(Owner owner) {
        return restaurantRepository.findAllByOwner(owner);
    }

    private Restaurant findRestaurantByNameAndAddress(String name, String address) {
        return restaurantRepository.findOneByNameAndAddress(name, address)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input restaurant name: %s, address: %s, no such elementException", name, address)));
    }
}
