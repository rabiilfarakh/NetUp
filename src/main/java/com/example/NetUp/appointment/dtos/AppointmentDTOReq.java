package com.example.NetUp.appointment.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record AppointmentDTOReq(
        @NotBlank(message = "Le titre ne peut pas être vide")
        @Size(min = 2, max = 100, message = "Le titre doit contenir entre 2 et 100 caractères")
        String title,

        @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
        String description,

        @NotNull(message = "La date de début est requise")
        @FutureOrPresent(message = "La date de début doit être présente ou future")
        LocalDateTime startTime,

        @NotNull(message = "La date de fin est requise")
        @FutureOrPresent(message = "La date de fin doit être présente ou future")
        LocalDateTime endTime,

        @NotBlank(message = "Le lieu ne peut pas être vide")
        @Size(min = 2, max = 200, message = "Le lieu doit contenir entre 2 et 200 caractères")
        String location,

        @NotNull(message = "L'ID du créateur est requis")
        @Positive(message = "L'ID du créateur doit être positif")
        Long creatorId
) {
}