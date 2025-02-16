package com.example.NetUp.community.dtos;

public record CommunityDTOReq(
        String name,
        String description,
        int quantity
) {}