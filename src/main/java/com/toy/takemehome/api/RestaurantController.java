package com.toy.takemehome.api;

import com.toy.takemehome.dto.delivery.DeliveryPrice;
import com.toy.takemehome.dto.restaurant.RestaurantDetail;
import com.toy.takemehome.dto.restaurant.RestaurantFindAllResponse;
import com.toy.takemehome.dto.restaurant.RestaurantSaveRequest;
import com.toy.takemehome.dto.restaurant.RestaurantUpdateRequest;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.service.RestaurantService;
import com.toy.takemehome.utils.DefaultRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public DefaultRes<Long> saveWithOwner(@RequestBody RestaurantSaveRequest saveRequest) {
        try {
            Long id = restaurantService.saveWithOwner(saveRequest);
            return DefaultRes.res(OK, CREATE_RESTAURANT, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_RESTAURANT_FAIL);
        }
    }

    @GetMapping("/restaurant/{id}")
    public DefaultRes<RestaurantDetail> findOneById(@PathVariable("id") Long id) {
        try {
            final Restaurant restaurant = restaurantService.findOneById(id);
            final RestaurantDetail restaurantDetail = new RestaurantDetail(restaurant);
            return DefaultRes.res(OK, FIND_RESTAURANT, restaurantDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, NOT_FOUND_RESTAURANT);
        }
    }

    @PutMapping("/restaurant/{id}")
    public DefaultRes<Long> update(@PathVariable("id") Long id,
                                   @RequestBody RestaurantUpdateRequest updateRequest) {
        try {
            restaurantService.update(id, updateRequest);
            return DefaultRes.res(OK, UPDATE_RESTAURANT, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, UPDATE_RESTAURANT_FAIL);
        }
    }

    @DeleteMapping("/restaurant/{id}")
    public DefaultRes<Long> delete(@PathVariable("id") Long id) {
        try {
            restaurantService.delete(id);
            return DefaultRes.res(OK, DELETE_RESTAURANT, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, DELETE_RESTAURANT_FAIL);
        }
    }

    @GetMapping
    public DefaultRes<RestaurantFindAllResponse> findAll() {
        try {
            final List<Restaurant> restaurants = restaurantService.findAll();
            final RestaurantFindAllResponse restaurantFindAllResponse = new RestaurantFindAllResponse(restaurants);
            return DefaultRes.res(OK, FIND_RESTAURANT, restaurantFindAllResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, NOT_FOUND_RESTAURANT);
        }
    }

    @GetMapping("/{ownerId}")
    public DefaultRes<RestaurantFindAllResponse> findAllByOwner(@PathVariable("ownerId") Long ownerId) {
        try {
            final List<Restaurant> restaurants = restaurantService.findAllByOwner(ownerId);
            final RestaurantFindAllResponse restaurantFindAllResponse = new RestaurantFindAllResponse(restaurants);
            return DefaultRes.res(OK, FIND_RESTAURANT, restaurantFindAllResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_RESTAURANT);
        }
    }

    @GetMapping("/restaurant/{restaurantId}/{customerId}/distance")
    public DefaultRes<DeliveryPrice> findDeliveryPrice(@PathVariable("restaurantId") Long restaurantId,
                                                       @PathVariable("customerId") Long customerId) {
        try {
            final int price = restaurantService.findDeliveryPrice(restaurantId, customerId);
            final DeliveryPrice deliveryPrice = new DeliveryPrice(price);
            return DefaultRes.res(OK, FIND_DELIVERY_PRICE, deliveryPrice);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_DELIVERY_PRICE);
        }
    }
}
