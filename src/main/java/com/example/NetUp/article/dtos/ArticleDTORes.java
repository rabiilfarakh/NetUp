package com.example.NetUp.article.dtos;

import java.time.LocalDateTime;

public record ArticleDTORes(
        String title,
        String description,
        LocalDateTime date
) {}
