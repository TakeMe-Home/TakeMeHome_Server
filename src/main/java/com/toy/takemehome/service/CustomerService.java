package com.toy.takemehome.service;

import com.toy.takemehome.dto.common.LoginRequest;
import com.toy.takemehome.dto.customer.CustomerSignUpRequest;
import com.toy.takemehome.dto.customer.CustomerUpdateRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.entity.restaurant.Restaurant;
import com.toy.takemehome.repository.CustomerRepository;
import com.toy.takemehome.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Long signUp(CustomerSignUpRequest signUpRequest) {
        checkDuplicateEmail(signUpRequest.getEmail());

        Customer createCustomer = Customer.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .address(signUpRequest.getAddress())
                .location(signUpRequest.getLocation())
                .build();

        customerRepository.save(createCustomer);

        return createCustomer.getId();
    }

    @Transactional
    public Long login(LoginRequest loginRequest) {
        final Customer customer = findCustomerByEmailPassword(loginRequest.getEmail(), loginRequest.getPassword());
        customer.login(loginRequest.getToken());
        return customer.getId();
    }

    @Transactional
    public void logout(Long id) {
        final Customer customer = findCustomerById(id);
        customer.logout();
    }

    public Customer findOneById(Long id) {
        final Customer findCustomer = findCustomerById(id);
        return findCustomer;
    }

    @Transactional
    public void update(Long id, CustomerUpdateRequest updateRequest) {
        Customer customer = findCustomerById(id);
        if (customer.isNotEqualsEmail(updateRequest.getEmail())) {
            checkDuplicateEmail(updateRequest.getEmail());
        }
        customer.update(updateRequest.getName(), updateRequest.getEmail(), updateRequest.getPassword(),
                updateRequest.getPhoneNumber(), updateRequest.getAddress(), updateRequest.getLocation());
    }

    @Transactional
    public void delete(Long id) {
        final Customer findCustomer = findCustomerById(id);
        customerRepository.delete(findCustomer);
    }

    public String findOwnerToken(Long restaurantId) {
        final Restaurant restaurant = findRestaurantByIdWithOwner(restaurantId);
        return restaurant.findOwnerToken();
    }

    private void checkDuplicateEmail(String email) {
        final Optional<Customer> findCustomer = customerRepository.findByEmail(email);
        if (findCustomer.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("input email: %s, customer signUp email duplicate!", email)
            );
        }
    }

    private Customer findCustomerByEmailPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("customer login fail!! mismatch email or password"));
    }

    private Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("input customer id: %d, no such elementException", id)));
    }

    private Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException(String.format("input restaurant id: %d, no such elementException", restaurantId)));
    }

    private Restaurant findRestaurantByIdWithOwner(Long restaurantId) {
        return restaurantRepository.findOneByIdWithOwner(restaurantId)
                .orElseThrow(() -> new NoSuchElementException(String.format("input restaurant id: %d, no such elementException", restaurantId)));
    }
}
