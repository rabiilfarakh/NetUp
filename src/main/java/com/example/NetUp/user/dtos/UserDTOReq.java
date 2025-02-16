package com.example.NetUp.user.dtos;

import java.time.LocalDateTime;

public record UserDTOReq(
        String firstName,
        String lastName,
        String email,
        LocalDateTime birthday,
        String password,
        String address,
        String experience,
        String location,
        String photo
) {}
