package com.toy.takemehome.api;

import com.toy.takemehome.dto.customer.CustomerDetail;
import com.toy.takemehome.dto.customer.CustomerSignUpRequest;
import com.toy.takemehome.dto.customer.CustomerUpdateRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.service.CustomerService;
import com.toy.takemehome.utils.DefaultRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

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

    @GetMapping("/customer/{id}")
    public DefaultRes<CustomerDetail> findOne(@PathVariable Long id) {
        try {
            final Customer findCustomer = customerService.findOneById(id);
            final CustomerDetail customerDetail = new CustomerDetail(findCustomer);
            return DefaultRes.res(OK, CREATE_CUSTOMER, customerDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_CUSTOMER_FAIL);
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
    public DefaultRes<Long> delete(@PathVariable("id") Long id){
        try {
            customerService.delete(id);
            return DefaultRes.res(OK, DELETE_CUSTOMER, id);
        }catch (Exception e){
            return DefaultRes.res(BAD_REQUEST, DELETE_CUSTOMER_FAIL);
        }
    }
}
