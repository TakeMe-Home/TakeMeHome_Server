package com.toy.takemehome.entity.customer;

import com.toy.takemehome.entity.BaseTimeEntity;
import com.toy.takemehome.entity.Location;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Embedded
    private Location location;

    @Column
    private String token;

    @Builder
    public Customer(String name, @Email String email, String password, String phoneNumber,
                    String address, Location location, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.location = location;
        this.token = token;
    }

    public void update(String name, String email, String password, String phoneNumber, String address, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.location = location;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double calculateDistance(Location location) {
        return this.location.calculateDistance(location);
    }

    public void setToken(String totken) {
        this.token = totken;
    }

    public boolean isNotEqualsEmail(String email) {
        return !isEqualsEmail(email);
    }

    private boolean isEqualsEmail(String email) {
        return this.email.equals(email);
    }
}
