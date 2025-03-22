package com.example.NetUp.comment.mapper;

import com.example.NetUp.comment.dtos.CommentDTOReq;
import com.example.NetUp.comment.dtos.CommentDTORes;
import com.example.NetUp.comment.entities.Comment;
import com.example.NetUp.user.mapper.UserMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {

    CommentDTORes toDto(Comment comment);

    @Mapping(source = "user_id", target = "user.id")
    @Mapping(source = "article_id", target = "article.id")
    Comment toEntity(CommentDTOReq commentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentFromDto(CommentDTOReq commentDTOReq, @MappingTarget Comment comment);
}