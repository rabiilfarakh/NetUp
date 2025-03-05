package com.example.NetUp.article.dtos;

import java.time.LocalDate;

public record ArticleDTOReq(
        String title,
        String description,
        LocalDate date
) {}