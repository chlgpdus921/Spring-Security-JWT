package com.example.securityspring.security;

import com.example.securityspring.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";
    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<String> authorityGroupList = member.getAuthorityGroupList();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String authority : authorityGroupList){
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + authority.toUpperCase());
            authorities.add(grantedAuthority);
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<String> getAuthorityGroupList() {
      return member.getAuthorityGroupList();
    }
}
