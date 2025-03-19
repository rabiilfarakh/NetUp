package com.example.NetUp.article.dtos;

import com.example.NetUp.user.dtos.UserDTORes;
import java.time.LocalDateTime;


public record ArticleDTOCom(
        Long id,
        String photo,
        String title,
        String description,
        LocalDateTime date,
        UserDTORes user
) {}