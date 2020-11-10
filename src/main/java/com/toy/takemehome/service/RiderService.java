package com.toy.takemehome.service;

import com.toy.takemehome.dto.common.LoginRequest;
import com.toy.takemehome.dto.rider.RiderSignUpRequest;
import com.toy.takemehome.dto.rider.RiderUpdateRequest;
import com.toy.takemehome.entity.rider.Rider;
import com.toy.takemehome.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    @Transactional
    public Long signUp(RiderSignUpRequest signUpRequest) {
        checkDuplicateEmail(signUpRequest.getEmail());

        final Rider rider = Rider.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .build();

        riderRepository.save(rider);

        return rider.getId();
    }

    public Long login(LoginRequest loginRequest) {
        final Rider rider = findRiderByEmailPassword(loginRequest.getEmail(), loginRequest.getPassword());
        return rider.getId();
    }

    public Rider findOneById(Long id) {
        final Rider rider = findRiderById(id);
        return rider;
    }

    @Transactional
    public void update(Long id, RiderUpdateRequest updateRequest) {
        checkDuplicateEmail(updateRequest.getEmail());

        final Rider rider = findRiderById(id);
        rider.update(updateRequest.getName(), updateRequest.getEmail(), updateRequest.getPassword(),
                updateRequest.getPhoneNumber());
    }

    @Transactional
    public void delete(Long id) {
        final Rider rider = findRiderById(id);
        riderRepository.delete(rider);
    }

    public List<Rider> findAll() {
        final List<Rider> riders = findAllRider();
        return riders;
    }

    private Rider findRiderByEmailPassword(String email, String password) {
        return riderRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("rider login fail!! mismatch email or password"));
    }

    private Rider findRiderById(Long id) {
        return riderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("input rider id: %d, no such elementException", id)));
    }

    private void checkDuplicateEmail(String email) {
        final Optional<Rider> rider = riderRepository.findByEmail(email);
        if (rider.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("input email: %s, rider signUp email duplicate!", email));
        }
    }

    private List<Rider> findAllRider() {
        return riderRepository.findAll();
    }
}
