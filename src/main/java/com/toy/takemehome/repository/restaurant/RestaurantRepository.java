package com.toy.takemehome.repository.restaurant;

import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.entity.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantCustomRepository {

    @Query("select r from Restaurant r " +
            "join fetch r.owner " +
            "where r.id = :restaurantId")
    Optional<Restaurant> findOneByIdWithOwner(@Param("restaurantId") Long id);

    void deleteByOwner(Owner owner);
}
