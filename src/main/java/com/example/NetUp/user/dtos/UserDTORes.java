package com.example.NetUp.user.dtos;

import com.example.NetUp.community.dtos.CommunityDTORes;
import com.example.NetUp.user.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTORes(
        String id,
        String username,
        String email,
        LocalDate birthday,
        String address,
        int experience,
        String location,
        String photo,
        Role role,
        CommunityDTORes community
) {
}