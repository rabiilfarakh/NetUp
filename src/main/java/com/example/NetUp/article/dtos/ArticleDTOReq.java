package com.example.NetUp.article.dtos;

import java.time.LocalDate;

public record ArticleDTOReq(
        String photo,
        String title,
        String description,
        LocalDate date,
        Long user_id
) {}