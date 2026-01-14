package com.investmentbanking.dealpipeline.user.dto;


import com.investmentbanking.dealpipeline.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {

    private String id;
    private String username;
    private String email;
    private Role role;
    private boolean active;
    private Instant createdAt;
}
