package com.example.NetUp.user.dtos;

public record LoginRequest(
        String username,
        String password
) {}