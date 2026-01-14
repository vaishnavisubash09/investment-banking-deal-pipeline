package com.investmentbanking.dealpipeline.user.service;

import com.investmentbanking.dealpipeline.user.dto.UserCreateRequestDTO;
import com.investmentbanking.dealpipeline.user.model.Role;
import com.investmentbanking.dealpipeline.user.model.User;
import com.investmentbanking.dealpipeline.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @InjectMocks UserServiceImpl service;

    User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id("1")
                .username("john")
                .role(Role.USER)
                .active(true)
                .build();
    }

    void admin() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "admin", null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        );
    }

    void userAuth() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "john", null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
    }

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    // -------- CREATE --------

    @Test
    void adminCanCreateUser() {
        admin();
        when(userRepository.existsByUsername("john")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hash");
        when(userRepository.save(any())).thenReturn(user);

        UserCreateRequestDTO dto = new UserCreateRequestDTO();
        dto.setUsername("john");
        dto.setRole(Role.USER);

        assertNotNull(service.createUser(dto));
    }

    @Test
    void userCannotCreateUser() {
        userAuth();
        assertThrows(AccessDeniedException.class,
                () -> service.createUser(new UserCreateRequestDTO()));
    }

    // -------- GET USERS --------

    @Test
    void adminCanGetAllUsers() {
        admin();
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertEquals(1, service.getAllUsers().size());
    }

    @Test
    void userCannotGetUsers() {
        userAuth();
        assertThrows(AccessDeniedException.class, () -> service.getAllUsers());
    }

    // -------- UPDATE STATUS --------

    @Test
    void adminUpdatesStatus() {
        admin();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        service.updateUserStatus("1", false);

        assertFalse(user.isActive());
    }

    @Test
    void userCannotUpdateStatus() {
        userAuth();
        assertThrows(AccessDeniedException.class,
                () -> service.updateUserStatus("1", false));
    }

    // -------- CURRENT USER --------

    @Test
    void getCurrentUser() {
        userAuth();
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        assertEquals("john", service.getCurrentUser().getUsername());
    }

    @Test
    void currentUserNotFound() {
        userAuth();
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getCurrentUser());
    }
}
