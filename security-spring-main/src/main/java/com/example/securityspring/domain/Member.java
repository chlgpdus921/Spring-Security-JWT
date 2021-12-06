package com.example.securityspring.domain;

import com.example.securityspring.dto.MemberSignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @Column(name="member_email")
    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="member_email")
    private final Collection<Authority> authorities = new ArrayList<>();

    public Member(MemberSignupRequestDto request) {
        email = request.getEmail();
        password = request.getPassword();
    }

    public void addAuthorityGroupList(Authority authority){
        authorities.add(authority);
    }

    public Collection<String> getAuthorityGroupList(){
        Collection<String> authorityGroupList = new ArrayList<>();
        for(Authority authority: authorities){
            authorityGroupList.add(authority.getAuthority());
        }
        return authorityGroupList;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
