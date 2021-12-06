package com.example.securityspring.domain;

import com.example.securityspring.dto.MemberSignupRequestDto;
import com.example.securityspring.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @Column(name="member_email")
    private String email;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String authorityGroup;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<Authority> authorities = new ArrayList<>();

    public Member(MemberSignupRequestDto request) {
        email = request.getEmail();
        password = request.getPassword();
        name = request.getName();
        role = Role.USER;
        authorityGroup = request.getAuthorityGroup();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
