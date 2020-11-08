package com.toy.takemehome.repository.restaurant;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.takemehome.entity.owner.Owner;
import com.toy.takemehome.entity.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toy.takemehome.entity.restaurant.QRestaurant.*;

@Repository
@RequiredArgsConstructor
public class RestaurantCustomRepositoryImpl implements RestaurantCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Restaurant> findAllByOwner(Owner owner) {
        return jpaQueryFactory
                .selectFrom(restaurant)
                .where(ownerEq(owner))
                .fetch();
    }

    private BooleanExpression ownerEq(Owner owner) {
        return restaurant.owner.eq(owner);
    }
}
