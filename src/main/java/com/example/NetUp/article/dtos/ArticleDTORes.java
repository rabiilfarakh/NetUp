    package com.example.NetUp.article.dtos;

    import com.example.NetUp.comment.dtos.CommentDTORes;
    import com.example.NetUp.user.dtos.UserDTORes;
    import java.time.LocalDateTime;
    import java.util.List;


    public record ArticleDTORes(
            Long id,
            String photo,
            String title,
            String description,
            LocalDateTime date,
            UserDTORes user,
            List<CommentDTORes> comments
    ) {}
