package com.example.NetUp.comment.mapper;

import com.example.NetUp.comment.dtos.CommentDTOReq;
import com.example.NetUp.comment.dtos.CommentDTORes;
import com.example.NetUp.comment.entities.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "article.id", target = "articleDTORes.id")
    CommentDTORes toDto(Comment comment);

    @Mapping(source = "article_id", target = "article.id")
    Comment toEntity(CommentDTOReq commentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentFromDto(CommentDTOReq commentDTOReq, @MappingTarget Comment comment);
}
