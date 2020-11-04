package com.toy.takemehome.service;

import com.toy.takemehome.dto.customer.CustomerSignUpRequest;
import com.toy.takemehome.dto.customer.CustomerUpdateRequest;
import com.toy.takemehome.entity.customer.Customer;
import com.toy.takemehome.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

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

    public Customer findOneById(Long id) {
        final Customer findCustomer = findCustomerById(id);
        return findCustomer;
    }

    @Transactional
    public void update(Long id, CustomerUpdateRequest updateRequest) {
        checkDuplicateEmail(updateRequest.getEmail());

        Customer customer = findCustomerById(id);
        customer.update(updateRequest.getName(), updateRequest.getEmail(), updateRequest.getPassword(),
                updateRequest.getPhoneNumber(), updateRequest.getAddress(), updateRequest.getLocation());
    }

    @Transactional
    public void delete(Long id) {
        final Customer findCustomer = findCustomerById(id);
        customerRepository.delete(findCustomer);
    }

    private void checkDuplicateEmail(String email) {
        final Optional<Customer> findCustomer = customerRepository.findByEmail(email);
        if (findCustomer.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("input email: %s, customer signUp email duplicate!", email)
            );
        }
    }

    private Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("input customer id: %d, no such elementException", id)));
    }
}
