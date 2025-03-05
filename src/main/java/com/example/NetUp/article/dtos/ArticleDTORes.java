package com.example.NetUp.article.dtos;

import java.time.LocalDate;

public record ArticleDTORes(
        Long id,
        String title,
        String description,
        LocalDate date
) {}
