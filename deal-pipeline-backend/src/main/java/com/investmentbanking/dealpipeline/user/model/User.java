package com.investmentbanking.dealpipeline.user.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String email;

    private String password;   // BCrypt hash

    private Role role;         // USER / ADMIN

    private boolean active;

    private Instant createdAt;
}
