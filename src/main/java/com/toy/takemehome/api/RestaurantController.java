package com.toy.takemehome.api;

import com.toy.takemehome.dto.restaurant.RestaurantSaveRequest;
import com.toy.takemehome.service.RestaurantService;
import com.toy.takemehome.utils.DefaultRes;
import com.toy.takemehome.utils.ResponseMessage;
import com.toy.takemehome.utils.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
