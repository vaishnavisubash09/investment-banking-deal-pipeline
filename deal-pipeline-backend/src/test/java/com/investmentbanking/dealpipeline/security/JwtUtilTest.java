package com.investmentbanking.dealpipeline.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    JwtUtil jwtUtil = new JwtUtil();

    private User mockUser() {
        return new User(
                "john",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

    @Test
    void generateAndExtractUsername() {
        String token = jwtUtil.generateToken(mockUser());

        String username = jwtUtil.extractUsername(token);

        assertEquals("john", username);
    }

    @Test
    void extractRoles() {
        String token = jwtUtil.generateToken(mockUser());

        var roles = jwtUtil.extractRoles(token);

        assertEquals(List.of("ROLE_ADMIN"), roles);
    }

    @Test
    void tokenIsValid() {
        String token = jwtUtil.generateToken(mockUser());

        assertTrue(jwtUtil.isValid(token));
    }

    @Test
    void invalidTokenShouldFail() {
        assertFalse(jwtUtil.isValid("this.is.not.a.jwt"));
    }

    @Test
    void isTokenValidWithCorrectUsername() {
        String token = jwtUtil.generateToken(mockUser());

        assertTrue(jwtUtil.isTokenValid(token, "john"));
    }

    @Test
    void isTokenInvalidWithWrongUsername() {
        String token = jwtUtil.generateToken(mockUser());

        assertFalse(jwtUtil.isTokenValid(token, "bob"));
    }
}
