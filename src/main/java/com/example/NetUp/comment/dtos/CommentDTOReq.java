package com.example.NetUp.comment.dtos;

import java.time.LocalDateTime;

public record CommentDTOReq(
        String description,
        LocalDateTime date,
        Long article_id
) {}
