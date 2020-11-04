package com.toy.takemehome.service;

import com.toy.takemehome.dto.owner.OwnerSignUpRequest;
import com.toy.takemehome.dto.owner.OwnerUpdateRequest;
import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.repository.OwnerRepository;
import com.toy.takemehome.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Long signUp(OwnerSignUpRequest signUpRequest) {
        checkDuplicatePhoneNumber(signUpRequest.getEmail());

        final Owner owner = Owner.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .address(signUpRequest.getAddress())
                .build();

        ownerRepository.save(owner);

        return owner.getId();
    }

    public Owner findOneById(Long id) {
        final Owner owner = findOwnerById(id);
        return owner;
    }

    @Transactional
    public void update(Long id, OwnerUpdateRequest updateRequest) {
        final Owner owner = findOwnerById(id);

        owner.update(updateRequest.getName(), updateRequest.getEmail(), updateRequest.getPassword(),
                updateRequest.getPhoneNumber(), updateRequest.getAddress());
    }

    @Transactional
    public void delete(Long id) {
        final Owner owner = findOwnerById(id);
        restaurantRepository.deleteByOwner(owner);
        ownerRepository.delete(owner);
    }

    private void checkDuplicatePhoneNumber(String email) {
        final Optional<Owner> owner = ownerRepository.findByEmail(email);
        if (owner.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("input email: %s, owner signUp email duplicate!", email));
        }
    }

    private Owner findOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("input owner id: %d, no such elementException", ownerId)));

    }
}