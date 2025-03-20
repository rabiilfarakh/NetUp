package com.example.NetUp.user.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTOReq(
        @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
        @Size(min = 3, max = 30, message = "Le nom d'utilisateur doit contenir entre 3 et 30 caractères")
        String username,

        @NotBlank(message = "L'email ne peut pas être vide")
        @Email(message = "L'email doit être valide")
        @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
        String email,

        @NotNull(message = "La date de naissance est requise")
        @Past(message = "La date de naissance doit être dans le passé")
        LocalDate birthday,

        @NotBlank(message = "Le mot de passe ne peut pas être vide")
        @Size(min = 8, max = 100, message = "Le mot de passe doit contenir entre 8 et 100 caractères")
        String password,

        @Size(max = 200, message = "L'adresse ne peut pas dépasser 200 caractères")
        String address,

        @Min(value = 0, message = "L'expérience ne peut pas être négative")
        int experience,

        @Size(max = 100, message = "La localisation ne peut pas dépasser 100 caractères")
        String location,

        @Size(max = 255, message = "Le chemin de la photo ne peut pas dépasser 255 caractères")
        String photo,

        @Positive(message = "L'ID de la communauté doit être positif")
        Long community_id
) {}