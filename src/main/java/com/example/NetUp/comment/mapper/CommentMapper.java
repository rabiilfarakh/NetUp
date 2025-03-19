package com.example.NetUp.comment.mapper;

import com.example.NetUp.comment.dtos.CommentDTOReq;
import com.example.NetUp.comment.dtos.CommentDTORes;
import com.example.NetUp.comment.entities.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "article", target = "articleDTOCom")
    CommentDTORes toDto(Comment comment);

    Comment toEntity(CommentDTOReq commentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentFromDto(CommentDTOReq commentDTOReq, @MappingTarget Comment comment);
}
