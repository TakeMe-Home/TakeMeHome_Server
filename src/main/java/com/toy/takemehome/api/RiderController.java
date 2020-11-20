package com.toy.takemehome.api;

import com.toy.takemehome.dto.common.LoginRequest;
import com.toy.takemehome.dto.rider.RiderDetail;
import com.toy.takemehome.dto.rider.RiderSignUpRequest;
import com.toy.takemehome.dto.rider.RiderUpdateRequest;
import com.toy.takemehome.entity.rider.Rider;
import com.toy.takemehome.service.RiderService;
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
@RequestMapping("/api/v1/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping
    public DefaultRes<Long> signUp(@RequestBody RiderSignUpRequest signUpRequest) {
        try {
            Long id = riderService.signUp(signUpRequest);
            return DefaultRes.res(OK, CREATE_RIDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_RIDER_FAIL);
        }
    }

    @PostMapping("/login")
    public DefaultRes<Long> login(@RequestBody LoginRequest loginRequest) {
        try {
            Long id = riderService.login(loginRequest);
            return DefaultRes.res(OK, LOGIN_SUCCESS, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, LOGIN_FAIL);
        }
    }

    @DeleteMapping("/rider/{id}/logout")
    public DefaultRes<Long> logout(@PathVariable("id") Long id) {
        try {
            riderService.logout(id);
            return DefaultRes.res(OK, LOGOUT_SUCCESS, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, LOGOUT_FAIL);
        }
    }

    @GetMapping("/rider/{id}")
    public DefaultRes<RiderDetail> findOne(@PathVariable Long id) {
        try {
            final Rider rider = riderService.findOneById(id);
            final RiderDetail riderDetail = new RiderDetail(rider);
            return DefaultRes.res(OK, FIND_RIDER, riderDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, NOT_FOUND_RIDER);
        }
    }

    @PutMapping("/{id}")
    public DefaultRes<Long> update(@PathVariable("id") Long id,
                                   @RequestBody RiderUpdateRequest updateRequest) {
        try {
            riderService.update(id, updateRequest);
            return DefaultRes.res(OK, UPDATE_RIDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, UPDATE_RIDER_FAIL);
        }
    }

    @DeleteMapping("/{id}")
    public DefaultRes<Long> delete(@PathVariable("id") Long id) {
        try {
            riderService.delete(id);
            return DefaultRes.res(OK, DELETE_RIDER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, DELETE_RIDER_FAIL);
        }
    }

    @GetMapping
    public DefaultRes<List<RiderDetail>> findAll() {
        try {
            final List<Rider> riders = riderService.findAll();
            final List<RiderDetail> riderDetails = riders.stream()
                    .map(RiderDetail::new)
                    .collect(Collectors.toList());
            return DefaultRes.res(OK, FIND_RIDER, riderDetails);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_RIDER);
        }
    }
}
