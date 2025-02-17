package com.example.NetUp.article.dtos;

import java.time.LocalDateTime;

public record ArticleDTOReq(
        String title,
        String description,
        LocalDateTime date
) {}