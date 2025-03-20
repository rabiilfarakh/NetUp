package com.example.NetUp.article.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record ArticleDTOReq(
        @Size(max = 255, message = "Le chemin de la photo ne peut pas dépasser 255 caractères")
        String photo,

        @NotBlank(message = "Le titre ne peut pas être vide")
        @Size(min = 2, max = 100, message = "Le titre doit contenir entre 2 et 100 caractères")
        String title,

        @NotBlank(message = "La description ne peut pas être vide")
        @Size(min = 10, max = 2000, message = "La description doit contenir entre 10 et 2000 caractères")
        String description,

        @NotNull(message = "La date est requise")
        @PastOrPresent(message = "La date doit être dans le passé ou présente")
        LocalDateTime date,

        @NotNull(message = "L'ID de l'utilisateur est requis")
        @Positive(message = "L'ID de l'utilisateur doit être positif")
        Long user_id
) {}