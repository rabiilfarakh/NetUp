package com.example.NetUp.comment.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CommentDTOReq(
        @NotBlank(message = "La description ne peut pas être vide")
        @Size(min = 2, max = 500, message = "La description doit contenir entre 2 et 500 caractères")
        String description,

        @NotNull(message = "La date est requise")
        @PastOrPresent(message = "La date doit être dans le passé ou présente")
        LocalDateTime date,

        @NotNull(message = "L'ID de l'article est requis")
        @Positive(message = "L'ID de l'article doit être positif")
        Long article_id,

        @NotNull(message = "L'ID de l'utilisateur est requis")
        @Positive(message = "L'ID de l'utilisateur doit être positif")
        Long user_id
) {}