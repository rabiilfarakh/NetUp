package com.example.NetUp.article.dtos;

import java.time.LocalDateTime;

public record ArticleDTORes(
        Long id,
        String title,
        String description,
        LocalDateTime date
) {}
