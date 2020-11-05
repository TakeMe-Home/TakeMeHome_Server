package com.toy.takemehome.repository;

import com.toy.takemehome.entity.rider.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByEmail(String email);
    Optional<Rider> findByEmailAndPassword(String email, String password);
}
