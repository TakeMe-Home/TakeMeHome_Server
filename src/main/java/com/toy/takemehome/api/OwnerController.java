package com.toy.takemehome.api;

import com.toy.takemehome.dto.common.LoginRequest;
import com.toy.takemehome.dto.owner.OwnerDetail;
import com.toy.takemehome.dto.owner.OwnerRestaurantSignUpRequest;
import com.toy.takemehome.dto.owner.OwnerSignUpRequest;
import com.toy.takemehome.dto.owner.OwnerUpdateRequest;
import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.service.OwnerService;
import com.toy.takemehome.utils.DefaultRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public DefaultRes<Long> signUp(@RequestBody OwnerSignUpRequest signUpRequest) {
        try {
            final Long id = ownerService.signUp(signUpRequest);
            return DefaultRes.res(OK, CREATE_OWNER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_OWNER_FAIL);
        }
    }

    @PostMapping("/login")
    public DefaultRes<Long> login(@RequestBody LoginRequest loginRequest) {
        try {
            Long id = ownerService.login(loginRequest);
            return DefaultRes.res(OK, LOGIN_SUCCESS, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, LOGIN_FAIL);
        }
    }

    @DeleteMapping("/owner/{id}/logout")
    public DefaultRes<Long> logout(@PathVariable("id") Long id) {
        try {
            ownerService.logout(id);
            return DefaultRes.res(OK, LOGOUT_SUCCESS, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, LOGOUT_FAIL);
        }
    }

    @GetMapping("/owner/{id}")
    public DefaultRes<OwnerDetail> findOneById(@PathVariable("id") Long id) {
        try {
            final Owner owner = ownerService.findOneById(id);
            final OwnerDetail ownerDetail = new OwnerDetail(owner);
            return DefaultRes.res(OK, FIND_OWNER, ownerDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_OWNER);
        }
    }

    @PutMapping("/owner/{id}")
    public DefaultRes<Long> update(@PathVariable("id") Long id,
                                   @RequestBody OwnerUpdateRequest updateRequest) {
        try {
            ownerService.update(id, updateRequest);
            return DefaultRes.res(OK, UPDATE_OWNER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, UPDATE_OWNER_FAIL);
        }
    }

    @DeleteMapping("/owner/{id}")
    public DefaultRes<Long> delete(@PathVariable("id") Long id) {
        try {
            ownerService.delete(id);
            return DefaultRes.res(OK, DELETE_OWNER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, DELETE_OWNER_FAIL);
        }
    }

    @GetMapping
    public DefaultRes<List<OwnerDetail>> findAll() {
        try {
            final List<Owner> owners = ownerService.findAll();
            final List<OwnerDetail> ownerDetails = owners.stream()
                    .map(OwnerDetail::new)
                    .collect(Collectors.toList());
            return DefaultRes.res(OK, FIND_ORDER, ownerDetails);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, NOT_FOUND_OWNER);
        }
    }

    @PostMapping("/with/restaurant")
    public DefaultRes<Long> signUpWithRestaurant(@RequestBody OwnerRestaurantSignUpRequest signUpRequest) {
        try {
            final Long ownerId = ownerService.signUpWithRestaurant(signUpRequest);
            return DefaultRes.res(OK, CREATE_OWNER, ownerId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_OWNER_FAIL);
        }
    }
}
