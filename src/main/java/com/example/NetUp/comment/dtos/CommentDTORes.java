package com.example.NetUp.comment.dtos;

import com.example.NetUp.article.dtos.ArticleDTOCom;

import java.time.LocalDateTime;

public record CommentDTORes(
        Long id,
        String description,
        LocalDateTime date,
        ArticleDTOCom articleDTOCom
) {}