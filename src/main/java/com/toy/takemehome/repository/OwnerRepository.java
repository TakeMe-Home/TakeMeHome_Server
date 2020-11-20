package com.toy.takemehome.repository;

import com.toy.takemehome.entity.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmail(String email);
    Optional<Owner> findByEmailAndPassword(String email, String password);
}
