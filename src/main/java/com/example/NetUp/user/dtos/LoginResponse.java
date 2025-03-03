package com.example.NetUp.user.dtos;

public record LoginResponse(
        String token,
        String role
) {}
