package com.example.securityspring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name="member_email")
    private String email;

    private String authority;

    public Authority(String email, String authority){
        this.email = email;
        this.authority = authority;
    }
}
