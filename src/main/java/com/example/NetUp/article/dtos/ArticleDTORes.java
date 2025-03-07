package com.example.NetUp.article.dtos;

import com.example.NetUp.user.dtos.UserDTORes;

import java.time.LocalDate;

public record ArticleDTORes(
        Long id,
        String photo,
        String title,
        String description,
        LocalDate date,
        UserDTORes user
) {}
