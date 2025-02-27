package com.example.NetUp.user.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTOReq(
        String username,
        String email,
        LocalDate birthday,
        String password,
        String address,
        String experience,
        String location,
        String photo,
        Long community_id
) {}
