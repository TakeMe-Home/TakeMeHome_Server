package com.toy.takemehome.entity.owner;

import com.toy.takemehome.entity.BaseTimeEntity;
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
public class Owner extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "owner_id")
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

    @Column
    private String token;

    @Builder
    public Owner(Long id, String name, @Email String email, String password, String phoneNumber, String address, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.token = token;
    }

    public void update(String name, @Email String email, String password, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void logout() {
        this.token = null;
    }

    public boolean isNotEqualsEmail(String email) {
        return !isEqualsEmail(email);
    }

    public boolean isEqualsEmail(String email){
        return this.email.equals(email);
    }
}
