package com.example.NetUp.user.dtos;

import com.example.NetUp.user.Role;

import java.time.LocalDateTime;

public record UserDTORes(
        String id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime birthday,
        String address,
        String experience,
        String location,
        String photo,
        Role role
) {}