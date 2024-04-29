package com.sbz.appa.config.filter;

import com.sbz.appa.config.utils.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Slf4j
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Generation JWT token");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecretKey key = Keys.hmacShaKeyFor(
                    SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .issuer("Appa Backend")
                    .subject("JWT token")
                    .claim("username", authentication.getName())
                    .claim("authorities", getAuthoritiesAsString(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 3000000))
                    .signWith(key)
                    .compact();
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/v1/users/login");
    }

    private String getAuthoritiesAsString(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = AuthorityUtils.authorityListToSet(authorities);
        return String.join(",", authoritiesSet);
    }
}
