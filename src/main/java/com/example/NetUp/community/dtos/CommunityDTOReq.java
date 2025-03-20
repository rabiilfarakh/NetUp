package com.example.NetUp.community.dtos;

import jakarta.validation.constraints.*;

public record CommunityDTOReq(
        @NotBlank(message = "Le nom ne peut pas être vide")
        @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
        String name,

        @NotBlank(message = "La description ne peut pas être vide")
        @Size(min = 10, max = 500, message = "La description doit contenir entre 10 et 500 caractères")
        String description
) {}