package com.example.NetUp.article.dtos;

import java.time.LocalDateTime;

public record ArticleDTOReq(
        String photo,
        String title,
        String description,
        LocalDateTime date,
        Long user_id
) {}