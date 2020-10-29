package com.toy.takemehome.repository;

import com.toy.takemehome.entity.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
