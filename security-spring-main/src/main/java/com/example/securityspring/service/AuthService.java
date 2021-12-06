package com.example.securityspring.service;

import com.example.securityspring.domain.Authority;
import com.example.securityspring.domain.Member;
import com.example.securityspring.dto.JwtRequestDto;
import com.example.securityspring.dto.JwtResponseDto;
import com.example.securityspring.dto.MemberSignupRequestDto;
import com.example.securityspring.repository.MemberRepository;
import com.example.securityspring.security.JwtProviderManager;
import com.example.securityspring.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtProviderManager jwtProviderManager;

    public JwtResponseDto login(JwtRequestDto request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        return createJwtToken(authentication);
    }

    private JwtResponseDto createJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtProviderManager.generateToken(principal);
        return new JwtResponseDto(token);
    }

    public Collection<Authority> signup(MemberSignupRequestDto request) {
        boolean existMember = memberRepository.existsById(request.getEmail());

        if (existMember) return null;

        Member member = new Member(request);
        member.encryptPassword(passwordEncoder);

        List<String> authorityGroupList = request.getAuthorityGroupList();
        for( String authorityGroup: authorityGroupList){
            member.addAuthorityGroupList(new Authority(request.getEmail(), authorityGroup));
        }

        memberRepository.save(member);
        return member.getAuthorities();
    }
}
