package com.example.NetUp.comment.dtos;

import com.example.NetUp.article.dtos.ArticleDTOCom;
import com.example.NetUp.user.dtos.EmbUserDTORes;
import com.example.NetUp.user.dtos.UserDTORes;

import java.time.LocalDateTime;

public record CommentDTORes(
        Long id,
        String description,
        LocalDateTime date,
        EmbUserDTORes user
) {}