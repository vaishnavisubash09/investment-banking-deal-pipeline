package com.investmentbanking.dealpipeline.security;


import com.investmentbanking.dealpipeline.user.model.Role;
import com.investmentbanking.dealpipeline.user.model.User;
import com.investmentbanking.dealpipeline.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUser_success() {
        User user = User.builder()
                .username("john")
                .password("hashed")
                .role(Role.ADMIN)
                .active(true)
                .build();

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        UserDetails details = service.loadUserByUsername("john");

        assertEquals("john", details.getUsername());
        assertTrue(details.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUser_notFound_shouldThrow() {
        when(userRepository.findByUsername("x"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("x"));
    }
}
