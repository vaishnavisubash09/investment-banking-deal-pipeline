package com.investmentbanking.dealpipeline.user.mapper;

import com.investmentbanking.dealpipeline.user.dto.UserResponseDTO;
import com.investmentbanking.dealpipeline.user.model.Role;
import com.investmentbanking.dealpipeline.user.model.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void toDTO_shouldMapAllFieldsCorrectly() {
        Instant now = Instant.now();

        User user = User.builder()
                .id("u1")
                .username("john")
                .email("john@bank.com")
                .role(Role.ADMIN)
                .active(true)
                .createdAt(now)
                .build();

        UserResponseDTO dto = mapper.toDTO(user);

        assertNotNull(dto);
        assertEquals("u1", dto.getId());
        assertEquals("john", dto.getUsername());
        assertEquals("john@bank.com", dto.getEmail());
        assertEquals(Role.ADMIN, dto.getRole());
        assertTrue(dto.isActive());
        assertEquals(now, dto.getCreatedAt());
    }

    @Test
    void toDTO_whenUserIsNull_shouldReturnNull() {
        UserResponseDTO dto = mapper.toDTO(null);
        assertNull(dto);
    }
}
