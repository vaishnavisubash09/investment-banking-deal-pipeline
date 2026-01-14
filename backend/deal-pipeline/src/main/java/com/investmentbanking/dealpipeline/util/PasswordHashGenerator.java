package com.investmentbanking.dealpipeline.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("admin123"));
        System.out.println(encoder.matches("admin123", "$2a$10$YjiPko/s6AW.hr6Rv85/RuoMx5q8hpkPh9qvc6GiuXaB3bU0fRpgW"));


    }
}

