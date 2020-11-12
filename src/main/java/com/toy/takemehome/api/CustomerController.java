package com.toy.takemehome.api;

import com.toy.takemehome.dto.common.LoginRequest;
import com.toy.takemehome.dto.customer.CustomerDetail;
import com.toy.takemehome.dto.customer.CustomerSignUpRequest;
import com.toy.takemehome.dto.customer.CustomerUpdateRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.service.CustomerService;
import com.toy.takemehome.service.fcm.FirebaseCloudMessageService;
import com.toy.takemehome.utils.DefaultRes;
import com.toy.takemehome.utils.notification.NotificationBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;
import static com.toy.takemehome.utils.notification.NotificationTitle.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping
    public DefaultRes<Long> signUp(@RequestBody CustomerSignUpRequest signUpRequest) {
        try {
            Long id = customerService.signUp(signUpRequest);
            return DefaultRes.res(OK, CREATE_CUSTOMER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_CUSTOMER_FAIL);
        }
    }

    @PostMapping("/login")
    public DefaultRes<Long> login(@RequestBody LoginRequest loginRequest) {
        try {
            Long id = customerService.login(loginRequest);
            return DefaultRes.res(OK, LOGIN_SUCCESS, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, LOGIN_FAIL);
        }
    }

    @GetMapping("/customer/{id}")
    public DefaultRes<CustomerDetail> findOne(@PathVariable Long id) {
        try {
            final Customer findCustomer = customerService.findOneById(id);
            final CustomerDetail customerDetail = new CustomerDetail(findCustomer);
            return DefaultRes.res(OK, FIND_CUSTOMER, customerDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, NOT_FOUND_CUSTOMER);
        }
    }

    @PutMapping("/{id}")
    public DefaultRes<Long> update(@PathVariable("id") Long id,
                                   @RequestBody CustomerUpdateRequest updateRequest) {
        try {
            customerService.update(id, updateRequest);
            return DefaultRes.res(OK, UPDATE_CUSTOMER, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, UPDATE_CUSTOMER_FAIL);
        }
    }

    @DeleteMapping("/{id}")
    public DefaultRes<Long> delete(@PathVariable("id") Long id) {
        try {
            customerService.delete(id);
            return DefaultRes.res(OK, DELETE_CUSTOMER, id);
        } catch (Exception e) {
            return DefaultRes.res(BAD_REQUEST, DELETE_CUSTOMER_FAIL);
        }
    }

    @PostMapping("/customer/order/{restaurantId}")
    public DefaultRes<String> order(@PathVariable("restaurantId") Long restaurantId) {
        try {
            final String token = customerService.findOwnerToken(restaurantId);
            firebaseCloudMessageService.sendMessageTo(token, ORDER_REQUEST, NotificationBody.ORDER_REQUEST);
            return DefaultRes.res(OK, CUSTOMER_ORDER, token);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CUSTOMER_ORDER_FAIL);
        }
    }
}
