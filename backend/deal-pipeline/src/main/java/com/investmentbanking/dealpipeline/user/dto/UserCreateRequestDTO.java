package com.investmentbanking.dealpipeline.user.dto;


import com.investmentbanking.dealpipeline.user.model.Role;
import lombok.Data;

@Data
public class UserCreateRequestDTO {

    private String username;
    private String email;
    private String password;
    private Role role;   // USER or ADMIN
}
