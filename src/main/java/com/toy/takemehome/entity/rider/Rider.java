package com.toy.takemehome.entity.rider;

import com.toy.takemehome.entity.BaseTimeEntity;
import com.toy.takemehome.entity.delivery.Delivery;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rider extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "rider_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String phoneNumber;
}
