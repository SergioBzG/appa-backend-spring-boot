package com.sbz.appa.config;

import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import com.sbz.appa.infrastructure.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<UserEntity> optionalUser = userRepository.findByEmail(username);
        log.info("Loading user");
        UserEntity user = optionalUser.orElseThrow(
                () -> new BadCredentialsException("Invalid credentials (email)")
        );
        if (passwordEncoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    List.of(new SimpleGrantedAuthority(user.getRole().getName()))
            );
        log.error("Invalid password");
        throw new BadCredentialsException("Invalid credentials (password)");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
