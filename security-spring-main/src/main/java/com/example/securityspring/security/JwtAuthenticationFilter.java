package com.example.securityspring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String BLANK = "";
    private final JwtProviderManager jwtProviderManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 받아옴
        String token = jwtProviderManager.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 확인
        if (StringUtils.isNotEmpty(token) && token.startsWith(BEARER_PREFIX)) {
            val jwtToken = token.replace(BEARER_PREFIX, BLANK);
            if (jwtProviderManager.validateToken(jwtToken)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
                Authentication authentication = jwtProviderManager.getAuthentication(jwtToken);
                // SecurityContext 에 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.debug("JWT Token does not begin with Bearer String");
        }

        chain.doFilter(request, response);
    }
}